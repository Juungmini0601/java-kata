package io.javakata.model.submission.result;

import io.javakata.model.submission.EvaluationRequest;
import io.javakata.model.submission.Status;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationResultSummary {

    private Long submitId;

    private Long userId;

    private Long problemId;

    private Status status;

    private int passedCount;

    private int failedCount;

    private List<TestCaseResult> testCaseResults;

    public static EvaluationResultSummary withInternalError(EvaluationRequest evaluationRequest) {
        List<TestCaseResult> testCaseResults = evaluationRequest.getTestCases()
            .stream()
            .map(TestCaseResult::withInternalError)
            .toList();

        return builder().submitId(evaluationRequest.getSubmitId())
            .userId(evaluationRequest.getUserId())
            .problemId(evaluationRequest.getProblemId())
            .status(Status.INTERNAL_ERROR)
            .passedCount(0)
            .failedCount(testCaseResults.size())
            .testCaseResults(testCaseResults)
            .build();
    }

    public static EvaluationResultSummary withCompileError(EvaluationRequest evaluationRequest) {
        List<TestCaseResult> testCaseResults = evaluationRequest.getTestCases()
            .stream()
            .map(TestCaseResult::withCompileError)
            .toList();

        return builder().submitId(evaluationRequest.getSubmitId())
            .userId(evaluationRequest.getUserId())
            .problemId(evaluationRequest.getProblemId())
            .status(Status.COMPILE_ERROR)
            .passedCount(0)
            .failedCount(testCaseResults.size())
            .testCaseResults(testCaseResults)
            .build();
    }

    public static EvaluationResultSummary from(EvaluationRequest evaluationRequest,
            List<TestCaseResult> testCaseResults) {
        int total = evaluationRequest.getTestCases().size();
        int passedCount = testCaseResults.stream()
            .filter(testCaseResult -> testCaseResult.getStatus() == Status.SUCCESS)
            .toList()
            .size();
        int failedCount = total - passedCount;
        Status status = Status.SUCCESS;

        if (total != passedCount) {
            status = testCaseResults.stream()
                .filter(testCaseResult -> testCaseResult.getStatus().isFailed())
                .findFirst()
                .get()
                .getStatus();
        }

        return builder().submitId(evaluationRequest.getSubmitId())
            .userId(evaluationRequest.getUserId())
            .problemId(evaluationRequest.getProblemId())
            .status(status)
            .passedCount(passedCount)
            .failedCount(failedCount)
            .testCaseResults(testCaseResults)
            .build();
    }

}
