package io.javakata.storage.db.core.problem;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProblemWithCategory {

    private Long id;

    private String title;

    private String level;

    private String categoryName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
