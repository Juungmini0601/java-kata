package io.javakata.core.problem.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.javakata.core.problem.api.response.ProblemResponse;
import io.javakata.core.problem.application.ProblemService;
import io.javakata.core.support.response.ApiResponse;
import io.javakata.model.problem.Problem;
import io.javakata.model.problem.ProblemWithCategory;
import io.javakata.storage.db.core.problem.ProblemListSearchParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping("/api/v1/problems/{problemId}")
    public ApiResponse<ProblemResponse> fetchProblem(@PathVariable Long problemId) {
        Problem problem = problemService.findById(problemId);

        return ApiResponse.success(ProblemResponse.from(problem));
    }

    @GetMapping("/api/v1/problems")
    public ApiResponse<Page<ProblemWithCategory>> fetchProblems(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) List<String> levels, @RequestParam(required = false) String keyword) {

        ProblemListSearchParam problemListSearchParam = new ProblemListSearchParam(pageable, keyword, categories,
                levels);

        Page<ProblemWithCategory> problems = problemService.fetchProblemList(problemListSearchParam);

        return ApiResponse.success(problems);
    }

    @GetMapping("/api/v1/problems/levels")
    public ApiResponse<List<String>> fetchLevels() {
        List<String> levels = problemService.getDistinctLevels();
        return ApiResponse.success(levels);
    }

}
