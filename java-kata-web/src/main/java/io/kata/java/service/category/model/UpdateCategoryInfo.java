package io.kata.java.service.category.model;

import java.time.LocalDateTime;

/**
 * @author    : kimjungmin
 * Created on : 2025. 2. 26.
 */
public record UpdateCategoryInfo(
	Long id,
	String name,
	String description,
	LocalDateTime updatedAt
) {
}
