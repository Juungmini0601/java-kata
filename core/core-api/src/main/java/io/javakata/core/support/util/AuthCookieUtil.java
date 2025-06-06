package io.javakata.core.support.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import io.javakata.model.auth.Token;
import jakarta.servlet.http.HttpServletResponse;

public final class AuthCookieUtil {

    public static final String ACCESS_TOKEN_COOKIE = "accessToken";

    public static final String REFRESH_TOKEN_COOKIE = "refreshToken";

    // 쿠키에 AccessToken, RefreshToken Setting
    public static void addAuthCookies(HttpServletResponse response, Token token) {
        ResponseCookie accessTokenCookie = ResponseCookie.from(ACCESS_TOKEN_COOKIE, token.getAccessToken())
            .path("/")
            .maxAge((int) ((token.getAccessTokenExpiredAt().getTime() - System.currentTimeMillis()) / 1000))
            .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE, token.getRefreshToken())
            .path("/")
            .maxAge((int) ((token.getRefreshTokenExpiredAt().getTime() - System.currentTimeMillis()) / 1000))
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }

    public static void removeAuthCookies(HttpServletResponse response) {
        ResponseCookie accessTokenCookie = ResponseCookie.from(ACCESS_TOKEN_COOKIE, "").path("/").maxAge(0).build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE, "").path("/").maxAge(0).build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }

    // Production용 코드 HTTPS, SameSite 옵션 추가 (CSRF 방어용)
    public static void addAuthCookiesEnhanced(HttpServletResponse response, Token token) {
        ResponseCookie accessTokenCookie = ResponseCookie.from(ACCESS_TOKEN_COOKIE, token.getAccessToken())
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge((int) ((token.getAccessTokenExpiredAt().getTime() - System.currentTimeMillis()) / 1000))
            .sameSite("None")
            .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE, token.getRefreshToken())
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge((int) ((token.getRefreshTokenExpiredAt().getTime() - System.currentTimeMillis()) / 1000))
            .sameSite("None")
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }

}