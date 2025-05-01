package io.javakata.model.problem;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProblemCategory {
	private Long id;
	private String name;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static ProblemCategory from(final String name) {
		return builder()
			.name(name)
			.build();
	}

	public void changeName(final String name) {
		this.name = name;
	}
}
