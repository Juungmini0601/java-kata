package io.javakata.core.notification.application;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import io.javakata.model.submission.result.SubmissionTestCaseResult;
import io.javakata.redis.core.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final Long EMITTER_CONNECTION_TIME = 60 * 60 * 1000L;

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    private final RedisService redisService;

    public SseEmitter connect(Long userId) {
        SseEmitter emitter = new SseEmitter(EMITTER_CONNECTION_TIME); // 60분 유지
        emitters.put(userId, emitter);

        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));
        emitter.onError((e) -> {
            log.error("error on notification emitters: {}", e.getCause(), e);
            emitters.remove(userId);
        });

        try {
            emitter.send(SseEmitter.event().name("INIT").data("connected"));
        }
        catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }

    // TODO 클라이언트에서 집계된 데이터를 기반으로 성공 실패 데이터 업데이트 하는 로직 필요
    public void sendTestCaseResult(SubmissionTestCaseResult result) {
        if (hasEmitter(result.getUserId())) {
            String submitKey = "submission:" + result.getSubmitId() + ":remaining";
            Long remaining = redisService.decrement(submitKey);
            send(result.getUserId(), "SUBMIT_RESULT", result);

            // 키 값에 남아 있는 테스트 케이스의 개수가 0이면 채점 완료 메세지 추가로 전송
            if (remaining != null && remaining == 0L) {
                send(result.getUserId(), "SUBMIT_COMPLETE", true);
                redisService.delete(submitKey);
            }
        }
    }

    private boolean hasEmitter(Long userId) {
        return this.emitters.containsKey(userId);
    }

    private void send(Long userId, String eventName, Object data) {
        SseEmitter emitter = emitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name(eventName).data(data));
            }
            catch (IOException e) {
                emitters.remove(userId);
            }
        }
    }

}
