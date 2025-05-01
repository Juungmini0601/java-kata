package io.javakata.core.auth.application;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.javakata.core.config.JwtConfig;
import io.javakata.model.auth.Token;
import io.javakata.model.auth.TokenClaim;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

/**
 * @author : kimjungmin Created on : 2025. 5. 1.
 */
@Service
@RequiredArgsConstructor
public class JwtTokenService implements TokenService {

    private final JwtConfig jwtConfig;

    @Override
    public Token generateToken(TokenClaim tokenClaim) {
        return new Token(generateAccessToken(tokenClaim), generateRefreshToken(tokenClaim));
    }

    @Override
    public TokenClaim parseToken(final String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtConfig.getRefreshToken().secret().getBytes());
        Jws<Claims> claimsJws = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);

        final String email = claimsJws.getPayload().getSubject();
        List<?> roles = claimsJws.getPayload().get("roles", List.class);

        return new TokenClaim(email, roles.stream().map(Object::toString).toList());
    }

    private String generateAccessToken(TokenClaim tokenClaim) {
        final long now = System.currentTimeMillis();
        Date expireDate = new Date(now + jwtConfig.getAccessToken().expire());
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtConfig.getAccessToken().secret().getBytes());

        return Jwts.builder()
            .subject(tokenClaim.subject())
            .claim("roles", tokenClaim.roles())
            .issuedAt(new Date(now))
            .expiration(expireDate)
            .signWith(secretKey)
            .compact();
    }

    private String generateRefreshToken(TokenClaim tokenClaim) {
        final long now = System.currentTimeMillis();
        Date expireDate = new Date(now + jwtConfig.getRefreshToken().expire());
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtConfig.getRefreshToken().secret().getBytes());

        return Jwts.builder()
            .subject(tokenClaim.subject())
            .claim("roles", tokenClaim.roles())
            .issuedAt(new Date(now))
            .expiration(expireDate)
            .signWith(secretKey)
            .compact();
    }

}
