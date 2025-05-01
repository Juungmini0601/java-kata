package io.javakata.storage.db.core.user;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

/**
 * @author : kimjungmin Created on : 2025. 5. 1.
 */
@Repository
@RequiredArgsConstructor
public class UserQuery {

    private final UserRepository userRepository;

    public boolean existsByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<UserEntity> findByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

}
