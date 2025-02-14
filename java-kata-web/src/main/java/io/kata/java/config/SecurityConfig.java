package io.kata.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	private static final String[] AUTH_ALLOWLIST = {
		"/swagger-ui/**",
		"/login/**",
		"/images/**",
		"/script/**",
		"/css/**"
	};

	// TODO https://github.com/Juungmini0601/DevLog-Server/blob/main/server/src/main/java/com/raon/devlog/config/SecurityConfig.java 참고해서 작성 예정
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// TODO API엔드 포인트는 CSRF 비활성화 왜 그래야 하는지 블로그!
		http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"));

		http.authorizeHttpRequests(authorize -> authorize
			.requestMatchers(AUTH_ALLOWLIST).permitAll()
			.requestMatchers("/user/signup/**").permitAll()
			.requestMatchers("/api/auth/email/verificationCode/**").permitAll()
			.anyRequest().authenticated()
		);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
