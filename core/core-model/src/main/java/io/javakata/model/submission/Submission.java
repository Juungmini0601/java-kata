package io.javakata.model.submission;

import io.javakata.model.language.Language;
import io.javakata.model.problem.Problem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Submission {

    private Long submissionId;

    private String code;

    private Status status;

    private Language language;

    private Long userId;

    private Problem problem;

    public static Submission from(Long userId, Language language, Status status, String code, Problem problem) {
        return builder().userId(userId).language(language).status(status).code(code).problem(problem).build();
    }

}
