package io.javakata.core.submission.application;

import io.javakata.core.notification.application.NotificationService;
import io.javakata.model.submission.event.SubmissionResultEvent;
import io.javakata.model.submission.result.EvaluationResultSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubmissionResultEventListener {

    private final NotificationService notificationService;

    @EventListener
    public void handleSubmissionResultEvent(SubmissionResultEvent event) {
        EvaluationResultSummary evaluationResultSummary = event.getEvaluationResultSummary();
        log.info("Received evaluation result summary: {}", evaluationResultSummary);
        notificationService.sendResultSummary(evaluationResultSummary);
    }

}