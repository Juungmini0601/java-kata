package io.javakata.model.auth;

import java.util.List;

public record TokenClaim(String subject, List<String> roles) {
}
