package io.javakata.storage.db.core.submission;

import io.javakata.model.language.Language;
import io.javakata.model.submission.Status;
import io.javakata.storage.db.core.BaseEntity;
import io.javakata.storage.db.core.problem.ProblemEntity;
import io.javakata.storage.db.core.user.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import lombok.ToString;

@ToString
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "submissions")
public class SubmissionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status; // 최종 결과를 표시하기 위한 컬럼 TODO 나중에 이쪽 컬럼을 테스트 케이스의 결과를 보고 잘 매핑해야함

    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String code;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    private ProblemEntity problemEntity;

    public static SubmissionEntity of(UserEntity userEntity, ProblemEntity problemEntity, Language language, String code, Status status) {
        return builder().userEntity(userEntity).problemEntity(problemEntity).language(language).code(code).status(status).build();
    }

}
