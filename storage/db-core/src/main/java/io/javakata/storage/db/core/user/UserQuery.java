package io.javakata.storage.db.core.user;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.javakata.model.user.User;
import io.javakata.storage.db.core.error.ConflictException;
import io.javakata.storage.db.core.error.NotFoundException;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserQuery {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public void ifExistsByEmailDoThrow(final String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException("중복된 이메일: " + email);
        }
    }

    @Transactional(readOnly = true)
    public Optional<UserEntity> findByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public User findByEmailOrElseThrow(final String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException("not found user email:" + email))
            .toModel();
    }

}
