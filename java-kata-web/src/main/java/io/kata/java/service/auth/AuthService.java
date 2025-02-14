package io.kata.java.service.auth;

import org.springframework.stereotype.Service;

import io.kata.java.core.mail.MailService;
import io.kata.java.core.redis.RedisService;
import io.kata.java.error.ErrorType;
import io.kata.java.error.JavaKataException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final VerificationCodeGenerator verificationCodeGenerator;
	private final RedisService redisService;
	private final MailService mailService;

	private static final String VERIFICATION_CODE_KEY_PREFIX = "verification_code_";

	public void sendVerificationCode(String email) {
		String verificationCode = verificationCodeGenerator.generateCode();
		redisService.set(VERIFICATION_CODE_KEY_PREFIX + email, verificationCode, 300);
		// TODO 메시지 템플릿 화 해서 뺄 예정
		mailService.sendEmail(email, "회원 가입 인증 코드입니다.", verificationCode);
	}

	public void checkVerificationCode(String email, String verificationCode) {
		String storedVerificationCode = redisService.get(VERIFICATION_CODE_KEY_PREFIX + email, String.class)
			.orElseThrow(() -> new RuntimeException("Verification code not found for email: " + email));

		if (!storedVerificationCode.equals(verificationCode)) {
			throw new JavaKataException(ErrorType.VALIDATION_ERROR, "인증번호가 일치하지 않습니다!");
		}

		redisService.delete(VERIFICATION_CODE_KEY_PREFIX + email);
	}
}
