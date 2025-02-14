package io.kata.java.controller.auth.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.kata.java.controller.auth.request.EmailVerificationCodeCheckRequest;
import io.kata.java.controller.auth.request.EmailVerificationCodeRequest;
import io.kata.java.response.ApiResponse;
import io.kata.java.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthRestController {

	private final AuthService authService;

	@PostMapping("/api/auth/email/verificationCode")
	public ApiResponse<String> sendEmailVerificationCode(@RequestBody EmailVerificationCodeRequest request) {
		authService.sendVerificationCode(request.email());

		return ApiResponse.success("인증 코드 발송이 완료 되었습니다.");
	}

	@PostMapping("/api/auth/email/verificationCode/check")
	public ApiResponse<String> checkEmailVerificationCode(
		@RequestBody EmailVerificationCodeCheckRequest request) {
		authService.checkVerificationCode(request.email(), request.verificationCode());

		return ApiResponse.success("인증이 완료 되었습니다.");
	}

}
