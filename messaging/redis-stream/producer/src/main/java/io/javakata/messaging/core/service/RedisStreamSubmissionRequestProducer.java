package io.javakata.messaging.core.service;

import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import io.javakata.model.submission.EvaluationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisStreamSubmissionRequestProducer {

    private static final String STREAM_KEY = "submission";

    private final RedisTemplate<String, String> redisTemplate;

    // RedisStreamPush
    public void produceSubmit(EvaluationRequest evaluationRequest) {
        ObjectRecord<String, EvaluationRequest> evaluationRecord = StreamRecords.newRecord()
            .ofObject(evaluationRequest)
            .withStreamKey(STREAM_KEY);

        RecordId recordId = redisTemplate.opsForStream().add(evaluationRecord);

        if (recordId == null) {
            log.error("error producing message for evaluation: {}", evaluationRequest.getSubmitId());
        }
    }

}
