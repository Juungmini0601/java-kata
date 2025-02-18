package io.kata.java.service.auth;

import org.jooq.generated.tables.pojos.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.kata.java.core.repository.UserQuery;
import io.kata.java.error.ErrorType;
import io.kata.java.error.JavaKataException;
import io.kata.java.service.auth.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserQuery userQuery;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users user = userQuery.findOneByEmail(email)
			.orElseThrow(() -> new JavaKataException(ErrorType.AUTHENTICATION_ERROR, "이메일 및 비밀 번호를 확인 해주세요"));

		return new CustomUserDetails(user);
	}
}
