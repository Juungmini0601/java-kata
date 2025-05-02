package io.javakata.model.problem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.javakata.model.testcase.TestCase;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class Problem {

    private Long id;

    private String title;

    private Level level;

    private String description;

    private String constraints;

    private String input;

    private String expectedOutput;

    private String baseCode;

    private Long memoryLimitMB;

    private Long timeLimitMS;

    private Long categoryId;

    private List<TestCase> testCases;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void addTestCase(TestCase testCases) {
        if (this.testCases == null) {
            this.testCases = new ArrayList<>();
        }

        this.testCases.add(testCases);
    }

    public void update(Problem updateInfo) {
        this.title = updateInfo.getTitle();
        this.level = updateInfo.getLevel();
        this.description = updateInfo.getDescription();
        this.constraints = updateInfo.getConstraints();
        this.input = updateInfo.getInput();
        this.expectedOutput = updateInfo.getExpectedOutput();
        this.baseCode = updateInfo.getBaseCode();
        this.memoryLimitMB = updateInfo.getMemoryLimitMB();
        this.timeLimitMS = updateInfo.getTimeLimitMS();
        this.categoryId = updateInfo.getCategoryId();
        this.updatedAt = LocalDateTime.now();

        List<TestCase> updateTestCases = new ArrayList<>();
        for (var updateTestCase : updateInfo.getTestCases()) {
            // 기존 테스트 케이스에서 정보를 업데이트 치고 넣어주기
            TestCase existingTestCase = testCases.stream()
                .filter(testCase -> testCase.getId().equals(updateTestCase.getId()))
                .findFirst()
                .orElse(null);

            if (existingTestCase != null) {
                updateTestCases.add(TestCase.builder()
                    .id(existingTestCase.getId())
                    .input(updateTestCase.getInput())
                    .expectedOutput(updateTestCase.getExpectedOutput())
                    .isPublic(updateTestCase.isPublic())
                    .createdAt(existingTestCase.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .build());
            }
            else {
                updateTestCases.add(updateTestCase);
            }
        }

        this.testCases = updateTestCases;
    }

    public List<TestCase> getTestCasesToRemove(List<TestCase> newTestCases) {
        return testCases.stream().filter(testCase -> !newTestCases.contains(testCase)).toList();
    }

    private List<Long> getTestCaseIds() {
        return getTestCases().stream().map(testCases -> testCases.getId()).toList();
    }

}
