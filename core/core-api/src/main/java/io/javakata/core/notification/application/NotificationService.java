package io.javakata.core.notification.application;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import io.javakata.model.submission.result.EvaluationResultSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final Long EMITTER_CONNECTION_TIME = 60 * 60 * 1000L;

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

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

    public void sendResultSummary(EvaluationResultSummary result) {
        log.info("notification 로직 호출");
        log.info("result: {}", result);
        log.info("emitters: {}", emitters.keySet());
        if (hasEmitter(result.getUserId())) {
            send(result.getUserId(), "SUBMIT_RESULT", result);
            send(result.getUserId(), "SUBMIT_COMPLETE", true);
            log.info("notification 완료");
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
