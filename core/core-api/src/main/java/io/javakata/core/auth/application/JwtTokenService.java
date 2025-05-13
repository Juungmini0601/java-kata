package io.javakata.core.auth.application;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.javakata.core.support.config.JwtConfig;
import io.javakata.model.auth.Token;
import io.javakata.model.auth.TokenClaim;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenService implements TokenService {

    private final JwtConfig jwtConfig;

    @Override
    public Token generateToken(TokenClaim tokenClaim) {
        // AccessToken 발급 준비
        final long now = System.currentTimeMillis();
        Date accessTokenExpireAt = new Date(now + jwtConfig.getAccessToken().expire());
        SecretKey accessTokenSecretKey = Keys.hmacShaKeyFor(jwtConfig.getAccessToken().secret().getBytes());
        // refreshToken 발급 준비
        Date refreshTokenExpireAt = new Date(now + jwtConfig.getRefreshToken().expire());
        SecretKey refreshTokenSecret = Keys.hmacShaKeyFor(jwtConfig.getRefreshToken().secret().getBytes());

        Date nowDate = new Date(now);

        final String accessToken = Jwts.builder()
            .subject(tokenClaim.getSubject())
            .claim("userId", tokenClaim.getUserId())
            .claim("roles", tokenClaim.getRoles())
            .issuedAt(nowDate)
            .expiration(accessTokenExpireAt)
            .signWith(accessTokenSecretKey)
            .compact();

        final String refreshToken = Jwts.builder()
            .subject(tokenClaim.getSubject())
            .claim("userId", tokenClaim.getUserId())
            .claim("roles", tokenClaim.getRoles())
            .issuedAt(nowDate)
            .expiration(refreshTokenExpireAt)
            .signWith(refreshTokenSecret)
            .compact();

        return Token.builder()
            .accessToken(accessToken)
            .accessTokenExpiredAt(accessTokenExpireAt)
            .accessTokenIssuedAt(nowDate)
            .refreshToken(refreshToken)
            .refreshTokenExpiredAt(refreshTokenExpireAt)
            .refreshTokenIssuedAt(nowDate)
            .build();
    }

    @Override
    public TokenClaim parseAccessToken(final String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtConfig.getAccessToken().secret().getBytes());
        Jws<Claims> claimsJws = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);

        final String email = claimsJws.getPayload().getSubject();
        // 한번에 Long으로 받으면에러가 발생함 Number.class로 가져와야 안전
        final Number userId = claimsJws.getPayload().get("userId", Number.class);

        List<?> roles = claimsJws.getPayload().get("roles", List.class);

        return new TokenClaim(email, userId.longValue(), roles.stream().map(Object::toString).toList());
    }

    @Override
    public TokenClaim parseRefreshToken(String refreshToken) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtConfig.getRefreshToken().secret().getBytes());
        Jws<Claims> claimsJws = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(refreshToken);

        final String email = claimsJws.getPayload().getSubject();
        // 한번에 Long으로 받으면에러가 발생함 Number.class로 가져와야 안전
        final Number userId = claimsJws.getPayload().get("userId", Number.class);

        List<?> roles = claimsJws.getPayload().get("roles", List.class);

        return new TokenClaim(email, userId.longValue(), roles.stream().map(Object::toString).toList());
    }

}
