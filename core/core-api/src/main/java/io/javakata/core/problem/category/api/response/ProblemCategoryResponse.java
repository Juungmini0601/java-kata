package io.javakata.core.problem.category.api.response;

import java.time.LocalDateTime;

import io.javakata.model.problem.ProblemCategory;
import lombok.Builder;

@Builder
public record ProblemCategoryResponse(
	Long id,
	String categoryName,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public static ProblemCategoryResponse from(ProblemCategory problemCategory) {
		return builder()
			.id(problemCategory.getId())
			.categoryName(problemCategory.getName())
			.createdAt(problemCategory.getCreatedAt())
			.updatedAt(problemCategory.getUpdatedAt())
			.build();
	}
}
