package io.javakata.core.problem.category.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.javakata.core.problem.category.api.response.ProblemCategoryResponse;
import io.javakata.core.problem.category.application.ProblemCategoryService;
import io.javakata.core.support.response.ApiResponse;
import io.javakata.model.problem.ProblemCategory;
import lombok.RequiredArgsConstructor;

/**
 * @author : kimjungmin Created on : 2025. 5. 1.
 */
@RestController
@RequiredArgsConstructor
public class ProblemCategoryController {

    private final ProblemCategoryService problemCategoryService;

    @GetMapping("/api/v1/problems/categories")
    public ApiResponse<List<ProblemCategoryResponse>> getProblemCategories() {
        List<ProblemCategory> problemCategories = problemCategoryService.findAll();
        List<ProblemCategoryResponse> responses = problemCategories.stream()
            .map(ProblemCategoryResponse::from)
            .toList();

        return ApiResponse.success(responses);
    }

}
