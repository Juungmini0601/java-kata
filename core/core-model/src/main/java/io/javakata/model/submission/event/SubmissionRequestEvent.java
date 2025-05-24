package io.javakata.model.submission.event;

import io.javakata.model.submission.EvaluationRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SubmissionRequestEvent {

    private final Object source;

    private final EvaluationRequest evaluationRequest;

}
