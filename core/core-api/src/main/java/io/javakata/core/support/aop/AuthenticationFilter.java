package io.javakata.core.support.aop;

import static io.javakata.core.support.util.AuthCookieUtil.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.javakata.core.auth.application.TokenService;
import io.javakata.core.support.error.ErrorType;
import io.javakata.core.support.response.ApiResponse;
import io.javakata.model.auth.Token;
import io.javakata.model.auth.TokenClaim;
import io.javakata.model.user.CurrentUser;
import io.jsonwebtoken.ExpiredJwtException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ParsedToken parsedToken = parseToken(request);
        // AccessToken이 존재 할 경우 유저 정보 파싱
        if (StringUtils.isNotBlank(parsedToken.accessToken())) {
            try {
                TokenClaim tokenClaim = tokenService.parseAccessToken(parsedToken.accessToken());

                request.setAttribute(CurrentUser.Current_USER_KEY, CurrentUser.from(tokenClaim));
            }
            catch (ExpiredJwtException e) {
                // AccessToken 만료시, RefreshToken 검사후 토큰 재발급 이후 요청 진행
                TokenClaim tokenClaim = tokenService.parseRefreshToken(parsedToken.refreshToken());
                Token newToken = tokenService.generateToken(tokenClaim);
                addAuthCookies(response, newToken);

                request.setAttribute(CurrentUser.Current_USER_KEY, CurrentUser.from(tokenClaim));
            }
            catch (Exception e) {
                // RefreshToken 파싱에도 예외가 있었을 경우
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                ApiResponse<?> error = ApiResponse.error(ErrorType.AUTHENTICATION_ERROR, "유효하지 않은 토큰입니다.");
                response.getWriter().write(objectMapper.writeValueAsString(error));
                removeAuthCookies(response);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 토큰 파싱, 토큰이 없어도 예외 던지지 않음
     */
    private ParsedToken parseToken(HttpServletRequest request) {
        String accessToken = null;
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return new ParsedToken(null, null);
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(ACCESS_TOKEN_COOKIE)) {
                accessToken = cookie.getValue();
                continue;
            }

            if (cookie.getName().equals(REFRESH_TOKEN_COOKIE)) {
                refreshToken = cookie.getValue();
            }
        }

        return new ParsedToken(accessToken, refreshToken);
    }

    private record ParsedToken(String accessToken, String refreshToken) {
    }

}
