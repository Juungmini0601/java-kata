package io.javakata.core.submission.application;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import io.javakata.model.submission.EvaluationRequest;
import io.javakata.model.submission.Submission;
import io.javakata.model.submission.event.SubmissionCreatedEvent;
import io.javakata.model.testcase.TestCase;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SubmissionCreatedEventListener {

    private final SubmissionRequestProducer submissionRequestProducer;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleSubmissionCreated(SubmissionCreatedEvent event) {
        Submission submission = event.getSubmission();
        List<TestCase> testCases = submission.getProblem().getTestCases();
        EvaluationRequest evaluationRequest = EvaluationRequest.from(submission, testCases);
        submissionRequestProducer.produceSubmit(evaluationRequest);
    }

}