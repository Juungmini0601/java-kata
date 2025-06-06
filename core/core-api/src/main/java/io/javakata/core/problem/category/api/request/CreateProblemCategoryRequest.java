package io.javakata.core.problem.category.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProblemCategoryRequest(@NotBlank(message = "카테고리는 필수 입력 값입니다.") @Size(min = 2, max = 250,
        message = "카테고리 이름은 2 - 250자 사이입니다.") String categoryName) {
}