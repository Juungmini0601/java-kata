package io.javakata.model.submission.event;

import io.javakata.model.submission.result.EvaluationResultSummary;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SubmissionResultEvent {

    private final Object source;

    private final String channel;

    private final EvaluationResultSummary evaluationResultSummary;

}
