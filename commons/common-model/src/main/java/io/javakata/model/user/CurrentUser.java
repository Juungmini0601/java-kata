package io.javakata.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CurrentUser {

	public static final String Current_USER_KEY = "CURRENT_USER";

	private String email;
	private String role;

	public CurrentUser(String email, String role) {
		this.email = email;
		this.role = role;
	}
}
