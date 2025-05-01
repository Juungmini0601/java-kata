package io.javakata.core.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.javakata.core.auth.application.PasswordEncoder;
import io.javakata.model.user.User;
import io.javakata.storage.db.core.user.UserCommand;
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
        userQuery.ifExistsByEmailDoThrow(email);

        final String encryptedPassword = passwordEncoder.encode(password);
        User user = User.withRegisterInfo(email, encryptedPassword, nickname);

        return userCommand.save(user);
    }

    @Transactional(readOnly = true)
    public User fetchUserByEmail(final String email) {
        return userQuery.findByEmailOrElseThrow(email);
    }

}
