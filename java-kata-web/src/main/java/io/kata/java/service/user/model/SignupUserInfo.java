package io.kata.java.service.user.model;

public record SignupUserInfo(
	String email,
	String password,
	String nickname
) {
}
