package io.javakata.consumer.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import io.javakata.model.language.Language;
import io.javakata.model.submission.EvaluationRequest;
import io.javakata.model.submission.result.EvaluationResultSummary;
import io.javakata.model.submission.result.TestCaseResult;
import io.javakata.model.testcase.TestCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 지금은 Java만 컴파일 하기 때문에 이런 구조가 가능한데, 실제로 다중 언어를 지원하려면 이미지 사이즈가 커질 가능성이 있음.
// 로직을 수행하다가 실패케이스가 하나라도 나온다면 실패니까 종료 시켜줄 것
// 0. DB 엔티티 마이그레이션 -> 프론트 배열도 바꿔줘야함.
// 1. JavaExecutor 각 케이스 별로 꼼꼼하게 처리해서 Pub/Sub 하기
// 1. 테스트케이스를 수행하면서 배열 만들기
// 2. 해당 배열을 기반으로 결과 메세지 만들어서 전송하기.
@Slf4j
@Component
@RequiredArgsConstructor
public class JavaExecutor implements CodeExecutor {

    private static final String SUBMIT_RESULT_CHANNEL = "submission.result";

    private final RedisTemplate<String, Object> redisTemplate;

    public void execute(EvaluationRequest evaluationRequest) {
        Path tempDir = null;
        try {
            // 디렉토리 생성
            tempDir = Files.createTempDirectory("submission_temp_" + evaluationRequest.getSubmitId());
            Path sourceFile = tempDir.resolve("Main.java");
            // 소스 코드 작성
            Files.writeString(sourceFile, evaluationRequest.getCode());
            // 자바 코드 컴파일
            Process compileProcess = new ProcessBuilder("javac", sourceFile.toString()).directory(tempDir.toFile())
                .redirectErrorStream(true)
                .start();

            boolean compiled = compileProcess.waitFor(5, TimeUnit.SECONDS);
            if (!compiled || compileProcess.exitValue() != 0) {
                String compileError = new String(compileProcess.getInputStream().readAllBytes());
                log.warn("compile failed: {}", compileError);
                EvaluationResultSummary resultSummary = EvaluationResultSummary.withCompileError(evaluationRequest);
                redisTemplate.convertAndSend(SUBMIT_RESULT_CHANNEL, resultSummary);
                return;
            }

            List<TestCaseResult> testCaseResults = new ArrayList<>();

            for (TestCase testCase : evaluationRequest.getTestCases()) {
                try {
                    ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", tempDir.toString(), "Main");
                    processBuilder.redirectErrorStream(true);
                    long startTime = System.currentTimeMillis();
                    Process process = processBuilder.start();

                    // 프로세스에 입력 추가
                    try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
                        bw.write(testCase.getInput());
                        bw.flush();
                    }

                    // 타임아웃 코드
                    boolean finished = process.waitFor(evaluationRequest.getTimeLimitMS(), TimeUnit.MILLISECONDS);

                    if (!finished) {
                        process.destroyForcibly();
                        log.warn("timeout");
                        testCaseResults.add(TestCaseResult.withTimeExceed(testCase));
                        break;
                    }

                    long endTime = System.currentTimeMillis();
                    long executionTime = endTime - startTime;

                    int exitCode = process.exitValue();

                    if (exitCode == 0) {
                        // 성공적으로 수행 완료 케이스
                        String result = new String(process.getInputStream().readAllBytes());

                        // 정답인 경우
                        if (flatten(result).equals(flatten(testCase.getExpectedOutput()))) {
                            testCaseResults.add(TestCaseResult.withSuccess(testCase, result, executionTime));
                        }
                        else {
                            // 오답인 경우
                            testCaseResults.add(TestCaseResult.withFail(testCase, result));
                            break;
                        }
                    }
                    else {
                        // 수행 실패 케이스
                        String errorMessage = new String(process.getInputStream().readAllBytes());
                        testCaseResults.add(TestCaseResult.withRuntimeError(testCase, errorMessage));
                        break;
                    }
                }
                catch (Exception e) {
                    log.error("error executing test case{}: {}", testCase.getId(), e.getMessage());
                    testCaseResults.add(TestCaseResult.withInternalError(testCase));
                    break;
                }
            }

            EvaluationResultSummary resultSummary = EvaluationResultSummary.from(evaluationRequest, testCaseResults);
            log.info("result summary: {}", resultSummary);
            redisTemplate.convertAndSend(SUBMIT_RESULT_CHANNEL, resultSummary);
            log.info("push 완료");
        }
        catch (Exception e) {
            log.error("error during compliation or execution: {}", e.getMessage());
            var resultSummary = EvaluationResultSummary.withInternalError(evaluationRequest);
            redisTemplate.convertAndSend(SUBMIT_RESULT_CHANNEL, resultSummary);
        }
        finally {
            try {
                Files.walkFileTree(tempDir, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        // 파일 삭제
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        // 디렉토리 삭제
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                });
            }
            catch (IOException e) {
                // 삭제 실패시 로그 출력
                log.error("Failed to delete temporary directory: {}", tempDir, e);
            }

        }
    }

    @Override
    public Language getSupportedLanguage() {
        return Language.JAVA_21;
    }

    private String flatten(String output) {
        return output.replaceAll("\\s+", ""); // 모든 공백 제거
    }

}
