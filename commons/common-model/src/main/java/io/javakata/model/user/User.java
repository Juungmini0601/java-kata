package io.javakata.model.user;

import java.time.LocalDateTime;

import io.javakata.model.auth.Role;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class User {
	private Long id;
	private String email;
	private String password;
	private String nickname;
	private Role role;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static User withRegisterInfo(final String email, final String password, final String nickname) {
		return User.builder()
			.email(email)
			.password(password)
			.nickname(nickname)
			.role(Role.ROLE_USER)
			.build();
	}
}
