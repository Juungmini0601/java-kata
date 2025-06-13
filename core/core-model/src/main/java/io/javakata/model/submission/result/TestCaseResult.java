package io.javakata.model.submission.result;

import io.javakata.model.submission.Status;
import io.javakata.model.testcase.TestCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseResult {

    private Long testCaseId;

    private Status status;

    private String expectedOutput;

    private String output;

    private Long timeUsedMS;

    public static TestCaseResult withInternalError(TestCase testCase) {
        return builder().testCaseId(testCase.getId())
            .status(Status.INTERNAL_ERROR)
            .expectedOutput(testCase.getExpectedOutput())
            .timeUsedMS(0L)
            .build();
    }

    public static TestCaseResult withCompileError(TestCase testCase) {
        return builder().testCaseId(testCase.getId())
            .status(Status.COMPILE_ERROR)
            .expectedOutput(testCase.getExpectedOutput())
            .timeUsedMS(0L)
            .build();
    }

    public static TestCaseResult withRuntimeError(TestCase testCase, String errorMessage) {
        return builder().testCaseId(testCase.getId())
            .status(Status.RUNTIME_ERROR)
            .expectedOutput(testCase.getExpectedOutput())
            .output(errorMessage)
            .timeUsedMS(0L)
            .build();
    }

    public static TestCaseResult withFail(TestCase testCase, String result) {
        return builder().testCaseId(testCase.getId())
            .status(Status.FAILED)
            .expectedOutput(testCase.getExpectedOutput())
            .output(result)
            .timeUsedMS(0L)
            .build();
    }

    public static TestCaseResult withTimeExceed(TestCase testCase) {
        return builder().testCaseId(testCase.getId())
            .status(Status.TIME_EXCEED)
            .expectedOutput(testCase.getExpectedOutput())
            .timeUsedMS(0L)
            .build();
    }

    public static TestCaseResult withSuccess(TestCase testCase, String result, Long executionTime) {
        return builder().testCaseId(testCase.getId())
            .status(Status.SUCCESS)
            .expectedOutput(testCase.getExpectedOutput())
            .output(result)
            .timeUsedMS(executionTime)
            .build();
    }

}
