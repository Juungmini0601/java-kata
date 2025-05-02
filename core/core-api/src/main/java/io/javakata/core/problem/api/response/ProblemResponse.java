package io.javakata.core.problem.api.response;

import java.time.LocalDateTime;
import java.util.List;

import io.javakata.core.testcase.api.response.TestCaseResponse;
import io.javakata.model.problem.Level;
import io.javakata.model.problem.Problem;
import lombok.Builder;

@Builder
public record ProblemResponse(Long id, String title, Level level, String description, String constraints, String input,
        String expectedOutput, String baseCode, Long memoryLimitMB, Long timeLimitMS, Long categoryId,
        LocalDateTime createdAt, LocalDateTime updatedAt, List<TestCaseResponse> testCases) {

    public static ProblemResponse from(Problem problem) {
        return ProblemResponse.builder()
            .id(problem.getId())
            .title(problem.getTitle())
            .level(problem.getLevel())
            .description(problem.getDescription())
            .constraints(problem.getConstraints())
            .input(problem.getInput())
            .expectedOutput(problem.getExpectedOutput())
            .baseCode(problem.getBaseCode())
            .memoryLimitMB(problem.getMemoryLimitMB())
            .timeLimitMS(problem.getTimeLimitMS())
            .categoryId(problem.getCategoryId())
            .createdAt(problem.getCreatedAt())
            .updatedAt(problem.getUpdatedAt())
            .testCases(problem.getTestCases().stream().map(TestCaseResponse::from).toList())
            .build();
    }

}