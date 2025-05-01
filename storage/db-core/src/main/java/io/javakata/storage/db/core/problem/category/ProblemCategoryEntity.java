package io.javakata.storage.db.core.problem.category;

import io.javakata.model.problem.ProblemCategory;
import io.javakata.storage.db.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author : kimjungmin Created on : 2025. 3. 25.
 */
@SuperBuilder
@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "problem_categories")
public class ProblemCategoryEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "category_name", unique = true)
	private String name;

	public static ProblemCategoryEntity from(ProblemCategory category) {
		return builder()
			.id(category.getId())
			.name(category.getName())
			.createdAt(category.getCreatedAt())
			.updatedAt(category.getUpdatedAt())
			.build();
	}

	public ProblemCategory toModel() {
		return ProblemCategory.builder()
			.id(getId())
			.name(getName())
			.createdAt(getCreatedAt())
			.updatedAt(getUpdatedAt())
			.build();
	}

}
