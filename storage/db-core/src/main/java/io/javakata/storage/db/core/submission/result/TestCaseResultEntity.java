package io.javakata.storage.db.core.submission.result;

import io.javakata.model.submission.Status;
import io.javakata.storage.db.core.BaseEntity;
import io.javakata.storage.db.core.submission.SubmissionEntity;
import io.javakata.storage.db.core.testcase.TestCaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "test_case_results")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TestCaseResultEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submission_id", nullable = false)
    private SubmissionEntity submissionEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_case_id", nullable = false)
    private TestCaseEntity testCaseEntity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status; // 각 테스트 케이스의 결과를 보기 위한 결과

    @Column(columnDefinition = "TEXT")
    private String output;

    @Column(columnDefinition = "TEXT")
    private String error;

    public static TestCaseResultEntity of(SubmissionEntity submissionEntity, TestCaseEntity testCaseEntity,
            Status status, String output, String error) {
        return TestCaseResultEntity.builder()
            .submissionEntity(submissionEntity)
            .testCaseEntity(testCaseEntity)
            .status(status)
            .output(output)
            .error(error)
            .build();
    }

}