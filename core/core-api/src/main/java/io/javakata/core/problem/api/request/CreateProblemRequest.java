package io.javakata.core.problem.api.request;

import java.util.List;

import io.javakata.core.problem.application.ProblemService;
import io.javakata.core.testcase.api.request.CreateTestCaseRequest;
import io.javakata.model.problem.Level;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProblemRequest {

    @NotBlank(message = "문제 제목은 필수입니다.")
    @Size(min = 2, max = 200, message = "문제 제목은 2~200자 사이여야 합니다.")
    private String title;

    @NotNull(message = "난이도는 필수입니다.")
    private Level level;

    @NotBlank(message = "문제 설명은 필수입니다.")
    private String description;

    @NotBlank(message = "입력 제한조건은 필수입니다.")
    private String constraints;

    @NotBlank(message = "입력 설명은 필수입니다.")
    private String input;

    @NotBlank(message = "기대 출력값 설명은 필수입니다.")
    private String expectedOutput;

    @NotBlank(message = "초기 코드는 필수입니다.")
    private String baseCode;

    @NotNull(message = "메모리 제한은 필수입니다.")
    @Min(1)
    private Long memoryLimitMB;

    @NotNull(message = "시간 제한은 필수입니다.")
    @Min(1)
    private Long timeLimitMS;

    @NotNull(message = "카테고리 Id는 필수입니다.")
    private Long categoryId;

    @NotNull
    @NotEmpty(message = "테스트케이스는 최소 1개 이상이어야 합니다.")
    private List<@Valid CreateTestCaseRequest> testCases;

    public ProblemService.CreateProblemCommand problemCommand() {
        return ProblemService.CreateProblemCommand.builder()
            .title(this.getTitle())
            .level(this.getLevel())
            .description(this.getDescription())
            .constraints(this.getConstraints())
            .input(this.getInput())
            .expectedOutput(this.getExpectedOutput())
            .baseCode(this.getBaseCode())
            .memoryLimitMB(this.getMemoryLimitMB())
            .timeLimitMS(this.getTimeLimitMS())
            .categoryId(this.getCategoryId())
            .build();
    }

    public List<ProblemService.CreateTestCaseCommand> testCaseCommand() {
        return testCases.stream()
            .map(testCase -> ProblemService.CreateTestCaseCommand.builder()
                .input(testCase.getInput())
                .expectedOutput(testCase.getExpectedOutput())
                .isPublic(testCase.isPublic())
                .build())
            .toList();
    }

}