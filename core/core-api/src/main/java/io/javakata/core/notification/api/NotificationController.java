package io.javakata.core.notification.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import io.javakata.core.config.aop.Auth;
import io.javakata.core.notification.application.NotificationService;
import io.javakata.core.user.application.UserService;
import io.javakata.model.user.CurrentUser;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    private final UserService userService;

    @GetMapping(value = "/api/v1/notifications/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(@Auth CurrentUser user) {
        Long userId = userService.fetchUserByEmail(user.getEmail()).getId();
        return notificationService.connect(userId);
    }

}