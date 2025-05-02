package io.javakata.model.testcase;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
@EqualsAndHashCode(of = "id")
public class TestCase {

    private Long id;

    private String input;

    private String expectedOutput;

    private boolean isPublic;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
