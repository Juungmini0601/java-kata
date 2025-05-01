package io.javakata.model.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

public record Token(String accessToken, String refreshToken) {
}
