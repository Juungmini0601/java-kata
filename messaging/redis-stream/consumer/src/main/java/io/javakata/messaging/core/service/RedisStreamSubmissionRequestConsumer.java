package io.javakata.messaging.core.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

import io.javakata.model.submission.EvaluationRequest;
import io.javakata.model.submission.event.SubmissionRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisStreamSubmissionRequestConsumer
        implements StreamListener<String, ObjectRecord<String, EvaluationRequest>> {

    private final RedisTemplate<String, String> redisTemplate;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void onMessage(ObjectRecord<String, EvaluationRequest> message) {
        EvaluationRequest evaluationRequest = message.getValue();
        SubmissionRequestEvent submissionRequestEvent = new SubmissionRequestEvent(this, evaluationRequest);
        eventPublisher.publishEvent(submissionRequestEvent);

        redisTemplate.opsForStream().delete(message);
    }

}
