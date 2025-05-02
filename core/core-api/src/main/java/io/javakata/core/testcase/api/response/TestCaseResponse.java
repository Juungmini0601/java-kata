package io.javakata.core.testcase.api.response;

import io.javakata.model.testcase.TestCase;
import lombok.Builder;

@Builder
public record TestCaseResponse(Long id, String input, String expectedOutput, boolean isPublic) {
    public static TestCaseResponse from(TestCase testCase) {
        return TestCaseResponse.builder()
            .id(testCase.getId())
            .input(testCase.getInput())
            .expectedOutput(testCase.getExpectedOutput())
            .isPublic(testCase.isPublic())
            .build();
    }
}
