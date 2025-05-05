package io.javakata.core.auth.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.javakata.core.support.error.ErrorType;
import io.javakata.core.support.error.JavaKataException;
import io.javakata.model.auth.Token;
import io.javakata.model.auth.TokenClaim;
import io.javakata.model.user.User;
import io.javakata.storage.db.core.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    @Transactional(readOnly = true)
    public Token signin(final String email, final String password) {
        User user = userRepository.findByEmailOrElseThrow(email);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new JavaKataException(ErrorType.AUTHENTICATION_ERROR, "로그인 실패");
        }

        TokenClaim tokenClaim = new TokenClaim(user.getEmail(), user.getId(), List.of(user.getRole().toString()));
        return tokenService.generateToken(tokenClaim);
    }

}
