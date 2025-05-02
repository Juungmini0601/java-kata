package io.javakata.core.problem.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.javakata.core.problem.api.request.CreateProblemRequest;
import io.javakata.core.problem.api.request.UpdateProblemRequest;
import io.javakata.core.problem.api.response.ProblemResponse;
import io.javakata.core.problem.application.ProblemService;
import io.javakata.core.support.response.ApiResponse;
import io.javakata.model.problem.Problem;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AdminProblemController {

    private final ProblemService problemService;

    @PostMapping("/api/v1/admin/problems")
    public ApiResponse<ProblemResponse> createProblem(@Valid @RequestBody CreateProblemRequest request) {
        Problem problem = problemService.createProblem(request.problemCommand(), request.testCaseCommand());

        return ApiResponse.success(ProblemResponse.from(problem));
    }

    @PutMapping("/api/v1/admin/problems/{problemId}")
    public ApiResponse<ProblemResponse> updateProblem(@PathVariable Long problemId,
            @Valid @RequestBody UpdateProblemRequest request) {
        Problem problem = problemService.updateProblem(request.problemCommand(problemId), request.testCaseCommand());

        return ApiResponse.success(ProblemResponse.from(problem));
    }

    @DeleteMapping("/api/v1/admin/problems/{problemId}")
    public ApiResponse<?> deleteProblem(@PathVariable Long problemId) {
        problemService.deleteProblem(problemId);

        return ApiResponse.success();
    }

}
