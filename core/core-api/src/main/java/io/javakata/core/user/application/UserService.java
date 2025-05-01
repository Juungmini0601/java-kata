package io.javakata.core.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.javakata.core.auth.application.PasswordEncoder;
import io.javakata.core.support.error.ErrorType;
import io.javakata.core.support.error.JavaKataException;
import io.javakata.model.user.User;
import io.javakata.storage.db.core.user.UserCommand;
import io.javakata.storage.db.core.user.UserEntity;
import io.javakata.storage.db.core.user.UserQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserCommand userCommand;

    private final UserQuery userQuery;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(final String email, final String password, final String nickname) {
        if (userQuery.existsByEmail(email)) {
            throw new JavaKataException(ErrorType.CONFLICT_ERROR, "중복된 이메일:" + email);
        }

        final String encryptedPassword = passwordEncoder.encode(password);
        UserEntity user = UserEntity.withRegisterInfo(email, encryptedPassword, nickname);
        return userCommand.save(user).toModel();
    }

    @Transactional(readOnly = true)
    public User fetchUserByEmail(final String email) {
        return userQuery.findByEmail(email)
            .orElseThrow(() -> new JavaKataException(ErrorType.NOT_FOUND_ERROR, "not found user email:" + email))
            .toModel();
    }

}
