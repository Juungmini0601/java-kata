package io.kata.java.controller.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailVerificationCodeCheckRequest(
	@NotBlank(message = "이메일은 필수 값입니다.")
	@Email(message = "이메일 형식으로 입력 해주세요.")
	String email,

	@NotBlank
	String verificationCode
) {
}
