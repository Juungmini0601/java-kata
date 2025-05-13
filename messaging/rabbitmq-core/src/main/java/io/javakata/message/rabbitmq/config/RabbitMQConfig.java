package io.javakata.message.rabbitmq.config;

import java.util.UUID;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String SUBMISSION_QUEUE_NAME = "submission";

    // RabbitMQ 큐 정의
    // false 파라미터는 큐가 휘발성인지 영속성인지를 지정하는 옵션
    // false로 설정하면 서버가 종료되거나 재시작될 때 큐의 메시지가 사라짐
    @Bean
    public Queue queue() {
        return new Queue(SUBMISSION_QUEUE_NAME, true);
    }

    /**
     * FanOut Exchange로부터 메시지를 받을 큐 큐 이름이 동일하면 다수의 서버가 있는 환경에서, 메시지를 못 받을 수 있기 때문에 큐 이름이
     * 고유 해야 함 UUID 충돌은 어쩔 수 없는거로..
     */
    @Bean
    public Queue submissionResultQueue() {
        return QueueBuilder.durable("submission.result." + UUID.randomUUID())
            .withArgument("x-dead-letter-exchange", "submission.dlx")
            .withArgument("x-dead-letter-routing-key", "submission.result.dlq")
            .build();
    }

    @Bean
    public FanoutExchange submissionExchange() {
        return new FanoutExchange("submission.exchange", true, false);
    }

    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange("submission.dlx");
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable("submission.result.dlq").build();
    }

    @Bean
    public Binding submissionResultBinding(Queue submissionResultQueue, FanoutExchange submissionExchange) {
        return BindingBuilder.bind(submissionResultQueue).to(submissionExchange);
    }

    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue, TopicExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with("submission.result.dlq");
    }

    // 메시지 통신시 JSON을 활용하도록 설정
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }

    // 메시지를 주고 받기 위한 빈 생성
    // Sender에서 rabbitTemplate.convertAndSend를 이용해서 전송
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());

        return rabbitTemplate;
    }

}
