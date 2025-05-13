package io.javakata.model.submission;

import io.javakata.model.language.Language;
import io.javakata.model.testcase.TestCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class TestCaseSubmission {

    private Long submitId;

    private Language language;

    private String code;

    private Long userId;

    private Long testCaseId;

    private String input;

    private String expectedOutput;

    private Long memoryLimitMB;

    private Long timeLimitMS;

    public static TestCaseSubmission from(Submission submission, TestCase testCase) {
        return builder().submitId(submission.getSubmissionId())
            .language(submission.getLanguage())
            .code(submission.getCode())
            .userId(submission.getUserId())
            .testCaseId(testCase.getId())
            .input(testCase.getInput())
            .expectedOutput(testCase.getExpectedOutput())
            .memoryLimitMB(submission.getProblem().getMemoryLimitMB())
            .timeLimitMS(submission.getProblem().getTimeLimitMS())
            .build();
    }

}
