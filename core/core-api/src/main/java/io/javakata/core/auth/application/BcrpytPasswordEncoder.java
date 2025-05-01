package io.javakata.core.auth.application;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * @author    : kimjungmin
 * Created on : 2025. 5. 1.
 */
@Component
public class BcrpytPasswordEncoder implements PasswordEncoder{
	@Override
	public String encode(String plainText) {
		// 솔트와 해시를 한 문자열에 저장해서 별도의 솔트 저장 필요 없음
		return BCrypt.hashpw(plainText, BCrypt.gensalt());
	}

	@Override
	public boolean matches(String plainText, String hashedText) {
		return BCrypt.checkpw(plainText, hashedText);
	}
}
