package io.javakata.messaging.core.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.javakata.model.submission.event.SubmissionResultEvent;
import io.javakata.model.submission.result.EvaluationResultSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisPubSubSubmissionConsumer implements MessageListener {

    private static final String CHANNEL_NAME = "submission.result";

    private final ApplicationEventPublisher eventPublisher;

    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String receivedChannel = new String(pattern);
            String payload = new String(message.getBody());

            if (CHANNEL_NAME.equals(receivedChannel)) {
                EvaluationResultSummary resultSummary = objectMapper.readValue(payload, EvaluationResultSummary.class);
                eventPublisher.publishEvent(new SubmissionResultEvent(this, receivedChannel, resultSummary));
            }
            else {
                log.warn("Ignored message from unrelated channel: {}", receivedChannel);
            }
        }
        catch (Exception e) {
            log.error("Failed to process Redis message", e);
        }

    }

}
