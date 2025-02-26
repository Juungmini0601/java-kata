package io.kata.java.controller.admin.category.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

/**
 * @author    : kimjungmin
 * Created on : 2025. 2. 26.
 */
public record CreateCategoryRequest(
	@NotBlank(message = "이름은 필수 값 입니다.")
	@Length(min = 4, max = 40, message = "이름은 4자리 에서 40자리 사이로 입력 해 주세요")
	String name,

	@NotBlank(message = "설명은 필수 값 입니다.")
	@Length(min = 4, max = 100, message = "설명은 4자리 에서 100자리 사이로 입력 해 주세요")
	String description
) {
}
