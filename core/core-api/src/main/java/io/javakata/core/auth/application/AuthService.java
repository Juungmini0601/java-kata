package io.javakata.core.auth.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.javakata.core.support.error.ErrorType;
import io.javakata.core.support.error.JavaKataException;
import io.javakata.model.auth.Token;
import io.javakata.model.auth.TokenClaim;
import io.javakata.storage.db.core.user.UserEntity;
import io.javakata.storage.db.core.user.UserQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserQuery userQuery;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    @Transactional(readOnly = true)
    public Token signin(final String email, final String password) {
        UserEntity userEntity = userQuery.findByEmail(email)
            .orElseThrow(() -> new JavaKataException(ErrorType.AUTHENTICATION_ERROR, "로그인 실패"));

        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new JavaKataException(ErrorType.AUTHENTICATION_ERROR, "로그인 실패");
        }

        TokenClaim tokenClaim = new TokenClaim(userEntity.getEmail(), List.of(userEntity.getRole().toString()));
        return tokenService.generateToken(tokenClaim);
    }

}
