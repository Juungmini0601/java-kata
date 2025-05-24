package io.javakata.messaging.core.config;

import java.time.Duration;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStreamCommands;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import io.javakata.model.submission.EvaluationRequest;

@Configuration
public class RedisStreamConfig {

    private static final String STREAM_KEY = "submission";

    private Subscription subscription;

    @Bean
    public Subscription subscription(RedisConnectionFactory connectionFactory,
            StreamListener<String, ObjectRecord<String, EvaluationRequest>> streamListener) {
        connectConsumerGroup(connectionFactory);

        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, EvaluationRequest>> options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
            .builder()
            .pollTimeout(Duration.ofMillis(100))
            .targetType(EvaluationRequest.class)
            .batchSize(1)
            .build();

        StreamMessageListenerContainer<String, ObjectRecord<String, EvaluationRequest>> container = StreamMessageListenerContainer
            .create(connectionFactory, options);

        subscription = container.receive(Consumer.from("submission-worker", "worker-" + UUID.randomUUID()),
                StreamOffset.create(STREAM_KEY, ReadOffset.lastConsumed()), streamListener);

        container.start();
        return subscription;
    }

    private void connectConsumerGroup(RedisConnectionFactory connectionFactory) {
        try {
            RedisConnection connection = connectionFactory.getConnection();
            RedisStreamCommands redisStreamCommands = connection.streamCommands();
            redisStreamCommands.xGroupCreate(STREAM_KEY.getBytes(), "submission-worker", ReadOffset.from("0"), true);
        }
        catch (Exception e) {
            // 타입 기반 체크: BUSYGROUP 예외는 무시
            if (!(e.getCause() instanceof io.lettuce.core.RedisBusyException)) {
                throw e;
            }
        }
    }

}
