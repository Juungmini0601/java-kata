package io.javakata.core.auth.application;

import io.javakata.model.auth.Token;
import io.javakata.model.auth.TokenClaim;

/**
 * @author : kimjungmin Created on : 2025. 5. 1.
 */
public interface TokenService {

    Token generateToken(TokenClaim tokenClaim);

    TokenClaim parseToken(final String token);

}
