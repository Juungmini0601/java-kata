package io.javakata.storage.db.core.problem;

import java.util.List;

import io.javakata.model.problem.Level;
import io.javakata.model.problem.Problem;
import io.javakata.storage.db.core.BaseEntity;
import io.javakata.storage.db.core.testcase.TestCaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@SuperBuilder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "problems")
public class ProblemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String constraints; // 입력 제한조건

    @Column(nullable = false, columnDefinition = "TEXT")
    private String input;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String expectedOutput; // 문제의 기대 출력값 설명

    @Column(nullable = false, columnDefinition = "TEXT")
    private String baseCode;

    @Column(nullable = false)
    private Long memoryLimitMB; // MB

    @Column(nullable = false)
    private Long timeLimitMS; // MS

    private Long categoryId; // 불필요한 연관관계 제거

    // TODO cascade, orphanRemoval 리뷰 받았던 내용이라 정리 필요
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "problem_id")
    private List<TestCaseEntity> testCases;

    public static ProblemEntity from(Problem problem) {
        List<TestCaseEntity> testCaseEntities = problem.getTestCases().stream().map(TestCaseEntity::from).toList();

        return builder().id(problem.getId())
            .title(problem.getTitle())
            .level(problem.getLevel())
            .description(problem.getDescription())
            .constraints(problem.getConstraints())
            .input(problem.getInput())
            .expectedOutput(problem.getExpectedOutput())
            .baseCode(problem.getBaseCode())
            .memoryLimitMB(problem.getMemoryLimitMB())
            .timeLimitMS(problem.getTimeLimitMS())
            .categoryId(problem.getCategoryId())
            .createdAt(problem.getCreatedAt())
            .updatedAt(problem.getUpdatedAt())
            .testCases(testCaseEntities)
            .build();
    }

    public Problem toModel() {
        return Problem.builder()
            .id(getId())
            .title(getTitle())
            .level(getLevel())
            .description(getDescription())
            .constraints(getConstraints())
            .input(getInput())
            .expectedOutput(getExpectedOutput())
            .baseCode(getBaseCode())
            .memoryLimitMB(getMemoryLimitMB())
            .timeLimitMS(getTimeLimitMS())
            .categoryId(getCategoryId())
            .createdAt(getCreatedAt())
            .updatedAt(getUpdatedAt())
            .testCases(testCases.stream().map(TestCaseEntity::toModel).toList())
            .build();
    }

}