package io.javakata.core.user.api.response;

import java.time.LocalDateTime;

import io.javakata.model.auth.Role;
import io.javakata.model.user.User;
import lombok.Builder;

@Builder
public record UserResponse(Long id, String email, String nickname, Role role, LocalDateTime createdAt,
        LocalDateTime updatedAt) {

    public static UserResponse from(User user) {
        return UserResponse.builder()
            .id(user.id())
            .email(user.email())
            .nickname(user.nickname())
            .role(user.role())
            .createdAt(user.createdAt())
            .updatedAt(user.updatedAt())
            .build();
    }
}
