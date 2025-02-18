package io.kata.java.controller.auth.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SigninRequest(
	@NotBlank(message = "이메일은 필수 값입니다.")
	@Email(message = "이메일 형식으로 입력 해주세요.")
	String email,

	@NotBlank(message = "비밀번호는 필수 값입니다.")
	@Length(min = 8, max = 40, message = "비밀번호는 8자리에서 40자리 사이로 입력해주세요")
	String password
) {
}
