package io.javakata.core.auth.application;

import io.javakata.model.auth.Token;
import io.javakata.model.auth.TokenClaim;

public interface TokenService {

    Token generateToken(TokenClaim tokenClaim);

    TokenClaim parseAccessToken(final String accessToken);

    TokenClaim parseRefreshToken(final String refreshToken);

}
