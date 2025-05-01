package io.javakata.core.problem.category.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.javakata.core.config.aop.Admin;
import io.javakata.core.problem.api.request.CreateProblemCategoryRequest;
import io.javakata.core.problem.category.api.request.UpdateProblemCategoryRequest;
import io.javakata.core.problem.category.api.response.ProblemCategoryResponse;
import io.javakata.core.problem.category.application.ProblemCategoryService;
import io.javakata.core.support.response.ApiResponse;
import io.javakata.model.problem.ProblemCategory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AdminProblemCategoryController {

    private final ProblemCategoryService problemCategoryService;

    @Admin
    @PostMapping("/api/v1/admin/problems/categories")
    public ApiResponse<ProblemCategoryResponse> createProblemCategory(
            @Valid @RequestBody CreateProblemCategoryRequest request) {
        ProblemCategory category = problemCategoryService.createCategory(request.categoryName());

        return ApiResponse.success(ProblemCategoryResponse.from(category));
    }

    @Admin
    @PutMapping("/api/v1/admin/problems/categories/{categoryId}")
    public ApiResponse<ProblemCategoryResponse> updateProblemCategory(@PathVariable Long categoryId,
            @Valid @RequestBody UpdateProblemCategoryRequest request) {
        ProblemCategory category = problemCategoryService.updateCategory(categoryId, request.categoryName());

        return ApiResponse.success(ProblemCategoryResponse.from(category));
    }

    @Admin
    @DeleteMapping("/api/v1/admin/problems/categories/{categoryId}")
    public ApiResponse<?> deleteProblemCategory(@PathVariable Long categoryId) {
        problemCategoryService.deleteCategory(categoryId);

        return ApiResponse.success();
    }

}
