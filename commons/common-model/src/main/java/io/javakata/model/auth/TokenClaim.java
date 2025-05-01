package io.javakata.model.auth;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenClaim {
	private String subject;
	private List<String> roles;
}
