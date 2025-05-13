package io.javakata.core.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.javakata.core.auth.application.PasswordEncoder;
import io.javakata.model.user.User;
import io.javakata.storage.db.core.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(final String email, final String password, final String nickname) {
        userRepository.ifExistsByEmailDoThrow(email);

        final String encryptedPassword = passwordEncoder.encode(password);
        User user = User.withRegisterInfo(email, encryptedPassword, nickname);

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User fetchUserByEmail(final String email) {
        return userRepository.findByEmailOrElseThrow(email);
    }

}
