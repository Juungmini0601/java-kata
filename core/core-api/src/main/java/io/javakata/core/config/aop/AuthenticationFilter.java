package io.javakata.core.config.aop;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.javakata.core.auth.application.TokenService;
import io.javakata.core.support.error.ErrorType;
import io.javakata.core.support.response.ApiResponse;
import io.javakata.model.auth.TokenClaim;
import io.javakata.model.user.CurrentUser;
import io.javakata.model.user.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * @author : kimjungmin Created on : 2025. 5. 1.
 */
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHENTICATION_HEADER = "Authorization";

    private static final String JWT_TOKEN_PREFIX = "Bearer ";

    private final TokenService tokenService;

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTHENTICATION_HEADER);
        String accessToken = null;

        if (request.getRequestURI().equals("/api/v1/notifications/stream")) {
            accessToken = request.getParameter("token");
        }
        else if (StringUtils.isNotBlank(authorizationHeader) && authorizationHeader.startsWith(JWT_TOKEN_PREFIX)) {
            accessToken = authorizationHeader.substring(7);
        }

        if (StringUtils.isNotBlank(accessToken)) {
            try {
                TokenClaim tokenClaim = tokenService.parseToken(accessToken);
                String role = tokenClaim.roles().get(0);
                request.setAttribute(CurrentUser.Current_USER_KEY, new CurrentUser(tokenClaim.subject(), role));
            }
            catch (ExpiredJwtException e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                ApiResponse<?> error = ApiResponse.error(ErrorType.TOKEN_EXPIRED_ERROR);

                response.getWriter().write(objectMapper.writeValueAsString(error));
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}
