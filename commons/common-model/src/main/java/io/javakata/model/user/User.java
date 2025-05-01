package io.javakata.model.user;

import java.time.LocalDateTime;

import io.javakata.model.auth.Role;
import lombok.Builder;

@Builder
public record User(Long id, String email, String nickname, Role role, LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
