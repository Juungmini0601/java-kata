package io.kata.java.service.auth;

import java.util.Random;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VerificationCodeGenerator {
	private static final Random RANDOM = new Random();

	public String generateCode() {
		return String.valueOf(RANDOM.nextInt(900000) + 100000);
	}

}
