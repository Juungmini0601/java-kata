package io.kata.java.controller.user.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
	@NotBlank(message = "이메일은 필수 값입니다.")
	@Email(message = "이메일 형식으로 입력 해주세요.")
	String email,

	@NotBlank(message = "비밀번호는 필수 값입니다.")
	@Length(min = 8, max = 40, message = "비밀번호는 8자리에서 40자리 사이로 입력해주세요")
	String password,

	@NotBlank(message = "비밀번호는 필수 값입니다.")
	@Length(min = 8, max = 40, message = "비밀번호는 8자리에서 40자리 사이로 입력해주세요")
	String passwordConfirm,

	@NotBlank(message = "닉네임은 필수 값입니다.")
	@Length(min = 2, max = 255, message = "닉네임은 2자리에서 255자리 사이로 입력해주세요.")
	String nickname
) {
}

