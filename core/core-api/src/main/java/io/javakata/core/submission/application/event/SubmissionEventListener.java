package io.javakata.core.submission.application.event;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import io.javakata.core.submission.application.SubmissionProducer;
import io.javakata.model.submission.Submission;
import io.javakata.model.submission.TestCaseSubmission;
import io.javakata.model.testcase.TestCase;
import io.javakata.redis.core.service.RedisService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SubmissionEventListener {

    private final RedisService redisService;

    private final SubmissionProducer submissionProducer;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleSubmissionCreated(SubmissionCreatedEvent event) {
        Submission submission = event.getSubmission();
        List<TestCase> testCases = submission.getProblem().getTestCases();

        // Redis에 테스트 케이스 개수 저장
        String submitKey = "submission:" + submission.getSubmissionId() + ":remaining";
        redisService.set(submitKey, testCases.size(), 600L);

        // 각 테스트 케이스마다 메시지 발행
        testCases.forEach(testCase -> submissionProducer.produceSubmit(TestCaseSubmission.from(submission, testCase)));
    }

}