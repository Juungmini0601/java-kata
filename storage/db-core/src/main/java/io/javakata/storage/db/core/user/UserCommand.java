package io.javakata.storage.db.core.user;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * @author    : kimjungmin
 * Created on : 2025. 5. 1.
 */
@Repository
@RequiredArgsConstructor
public class UserCommand {
	private final UserRepository userRepository;

	@Transactional
	public UserEntity save(UserEntity user) {
		return userRepository.save(user);
	}

	@Transactional
	public void delete(String email) {
		userRepository.deleteByEmail(email);
	}
}
