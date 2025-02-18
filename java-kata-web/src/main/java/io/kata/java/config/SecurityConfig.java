package io.kata.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import io.kata.java.service.auth.CustomAuthenticationFailureHandler;
import io.kata.java.service.auth.CustomAuthenticationSuccessHandler;
import io.kata.java.service.auth.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private static final String[] AUTH_ALLOWLIST = {
		"/swagger-ui/**",
		"/login/**",
		"/images/**",
		"/script/**",
		"/css/**"
	};

	private final CustomUserDetailsService customUserDetailsService;
	private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	// https://github.com/Juungmini0601/DevLog-Server/blob/main/server/src/main/java/com/raon/devlog/config/SecurityConfig.java 참고해서 작성 예정
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// TODO API엔드 포인트는 CSRF 비활성화 왜 그래야 하는지 블로그!
		http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"));

		http.authorizeHttpRequests(authorize -> authorize
			.requestMatchers(AUTH_ALLOWLIST).permitAll()
			.requestMatchers("/").permitAll()
			.requestMatchers("/auth/signin").permitAll()
			.requestMatchers("/user/signup/**").permitAll()
			.requestMatchers("/api/auth/email/verificationCode/**").permitAll()
			.anyRequest().authenticated()
		);

		http.formLogin(configurer ->
			configurer
				.loginPage("/auth/signin")
				.loginProcessingUrl("/auth/signin")
				.usernameParameter("email")
				.defaultSuccessUrl("/")
				.successHandler(customAuthenticationSuccessHandler)
				.failureHandler(customAuthenticationFailureHandler)
				.permitAll()
		);

		http.sessionManagement(session -> session
			.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 필요할 때만 세션 생성
			.maximumSessions(1) // 동시 로그인 1개로 제한
			.expiredUrl("/auth/signin") // 만료 시 이동할 URL
		);

		http.userDetailsService(customUserDetailsService);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
