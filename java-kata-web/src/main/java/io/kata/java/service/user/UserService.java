package io.kata.java.service.user;

import org.jooq.generated.tables.pojos.Users;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.kata.java.core.repository.UserCommand;
import io.kata.java.service.user.mapper.UserMapper;
import io.kata.java.service.user.model.SignupUserInfo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserCommand userCommand;

	public void signup(String email, String password, String nickname) {
		String encodedPassword = passwordEncoder.encode(password);
		SignupUserInfo signupUserInfo = new SignupUserInfo(email, encodedPassword, nickname);
		Users user = UserMapper.INSTANCE.toEntity(signupUserInfo);

		userCommand.save(user);
	}
}
