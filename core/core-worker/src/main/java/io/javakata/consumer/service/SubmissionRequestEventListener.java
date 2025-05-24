package io.javakata.consumer.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import io.javakata.model.submission.EvaluationRequest;
import io.javakata.model.submission.event.SubmissionRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubmissionRequestEventListener {

    private final ExecutorFactory executorFactory;

    @EventListener
    public void handleSubmissionRequestEvent(SubmissionRequestEvent event) {
        EvaluationRequest evaluationRequest = event.getEvaluationRequest();
        CodeExecutor executor = executorFactory.getExecutor(evaluationRequest.getLanguage());
        executor.execute(evaluationRequest);
    }

}
