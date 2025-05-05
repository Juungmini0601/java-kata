package io.javakata.core.submission.application;

import java.nio.charset.StandardCharsets;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.javakata.core.notification.application.NotificationService;
import io.javakata.model.submission.result.SubmissionTestCaseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubmissionResultConsumer {

    private final ObjectMapper objectMapper;

    private final NotificationService notificationService;

    @RabbitListener(queues = "#{submissionResultQueue.name}")
    public void onMessage(Message message) {
        try {
            String payload = new String(message.getBody(), StandardCharsets.UTF_8);
            SubmissionTestCaseResult result = objectMapper.readValue(payload, SubmissionTestCaseResult.class);
            notificationService.sendTestCaseResult(result);
        }
        catch (Exception e) {
            log.error("RabbitMQ 메시지 처리 실패", e);
        }
    }

}