package io.javakata.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import io.javakata.message.rabbitmq.config.RabbitMQConfig;
import io.javakata.model.submission.TestCaseSubmission;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SubmissionConsumer {

    @RabbitListener(queues = RabbitMQConfig.SUBMISSION_QUEUE_NAME)
    public void consumeSubmit(TestCaseSubmission message) {
        log.info("message consumed: {}", message);
    }

}
