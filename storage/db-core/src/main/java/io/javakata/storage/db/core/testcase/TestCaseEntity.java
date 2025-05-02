package io.javakata.storage.db.core.testcase;

import io.javakata.model.testcase.TestCase;
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

@ToString
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "test_cases")
public class TestCaseEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String input;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String expectedOutput;

    @Column(nullable = false)
    private boolean isPublic;

    public static TestCaseEntity from(TestCase testCase) {
        return TestCaseEntity.builder()
            .id(testCase.getId())
            .input(testCase.getInput())
            .expectedOutput(testCase.getExpectedOutput())
            .isPublic(testCase.isPublic())
            .createdAt(testCase.getCreatedAt())
            .updatedAt(testCase.getUpdatedAt())
            .build();
    }

    public TestCase toModel() {
        return TestCase.builder()
            .id(getId())
            .input(getInput())
            .expectedOutput(getExpectedOutput())
            .isPublic(isPublic())
            .createdAt(getCreatedAt())
            .updatedAt(getUpdatedAt())
            .build();
    }

    // @ManyToOne
    // @JoinColumn(name = "problem_id", nullable = false)
    // @JsonBackReference // testcase -> problem 직렬화 X TODO 어차피 DTO로 변환되면서 바뀔 가능성 있음
    // private Problem problem;
    //
    // public void update(UpdateTestCaseRequest request) {
    // this.input = request.getInput();
    // this.expectedOutput = request.getExpectedOutput();
    // this.isPublic = request.isPublic();
    // }
    //
    // public void setProblem(Problem problem) {
    // this.problem = problem;
    // }
    //
    // public static TestCase withCreateRequest(CreateTestCaseRequest request) {
    // return builder()
    // .input(request.getInput())
    // .expectedOutput(request.getExpectedOutput())
    // .isPublic(request.isPublic())
    // .build();
    // }

}
