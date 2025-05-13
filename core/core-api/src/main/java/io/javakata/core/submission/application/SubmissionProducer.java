package io.javakata.core.submission.application;

import static io.javakata.message.rabbitmq.config.RabbitMQConfig.*;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import io.javakata.core.support.error.ErrorType;
import io.javakata.core.support.error.JavaKataException;
import io.javakata.model.language.Language;
import io.javakata.model.submission.TestCaseSubmission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubmissionProducer {

    private final RabbitTemplate rabbitTemplate;

    public void produceSubmit(TestCaseSubmission submitTestCase) {
        Language language = submitTestCase.getLanguage();
        if (language == Language.JAVA_21) {
            rabbitTemplate.convertAndSend(SUBMISSION_QUEUE_NAME, submitTestCase);
            log.info("message produce: {}", submitTestCase);
        }
        else {
            log.error("제출 로직 확인 필요");
            throw new JavaKataException(ErrorType.VALIDATION_ERROR, "invalid language: " + language);
        }
    }

}