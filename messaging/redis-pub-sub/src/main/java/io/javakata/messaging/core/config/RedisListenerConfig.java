package io.javakata.messaging.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import io.javakata.messaging.core.service.RedisPubSubSubmissionConsumer;

@Configuration
public class RedisListenerConfig {

    private static final String SUBMISSION_RESULT_CHANNEL = "submission.result";

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
            RedisPubSubSubmissionConsumer messageProducer) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        container.addMessageListener(messageProducer, new ChannelTopic(SUBMISSION_RESULT_CHANNEL));
        return container;
    }

}
