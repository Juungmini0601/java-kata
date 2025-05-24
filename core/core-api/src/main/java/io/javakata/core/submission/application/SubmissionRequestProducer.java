package io.javakata.core.submission.application;

import org.springframework.stereotype.Component;

import io.javakata.messaging.core.service.RedisStreamSubmissionRequestProducer;
import io.javakata.model.submission.EvaluationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubmissionRequestProducer {

    private final RedisStreamSubmissionRequestProducer submissionRequestProducer;

    public void produceSubmit(EvaluationRequest evaluationRequest) {
        submissionRequestProducer.produceSubmit(evaluationRequest);
    }

}