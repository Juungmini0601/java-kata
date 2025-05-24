package io.javakata.model.submission;

import java.util.List;

import io.javakata.model.language.Language;
import io.javakata.model.testcase.TestCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EvaluationRequest {

    private Long submitId;

    private Long userId;

    private Long problemId;

    private String code;

    private Language language;

    private Long memoryLimitMB;

    private Long timeLimitMS;

    private List<TestCase> testCases;

    public static EvaluationRequest from(Submission submission, List<TestCase> testCases) {
        return builder().submitId(submission.getSubmissionId())
            .userId(submission.getUserId())
            .problemId(submission.getProblem().getId())
            .code(submission.getCode())
            .language(submission.getLanguage())
            .memoryLimitMB(submission.getProblem().getMemoryLimitMB())
            .timeLimitMS(submission.getProblem().getTimeLimitMS())
            .testCases(testCases)
            .build();
    }

}
