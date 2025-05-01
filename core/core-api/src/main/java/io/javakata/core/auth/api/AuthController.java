package io.javakata.core.auth.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.javakata.core.auth.api.request.SigninRequest;
import io.javakata.core.auth.api.response.TokenResponse;
import io.javakata.core.auth.application.AuthService;
import io.javakata.core.config.aop.Auth;
import io.javakata.core.support.response.ApiResponse;
import io.javakata.core.user.api.response.UserResponse;
import io.javakata.core.user.application.UserService;
import io.javakata.model.auth.Token;
import io.javakata.model.user.CurrentUser;
import io.javakata.model.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    @GetMapping("/api/v1/auth")
    public ApiResponse<UserResponse> getCurrentUser(@Auth CurrentUser user) {
        User fullUserInfo = userService.fetchUserByEmail(user.email());

        return ApiResponse.success(UserResponse.from(fullUserInfo));
    }

    @PostMapping("/api/v1/auth/token")
    public ApiResponse<TokenResponse> signin(@Valid @RequestBody SigninRequest request) {
        Token token = authService.signin(request.email(), request.password());

        return ApiResponse.success(new TokenResponse(token.accessToken(), token.refreshToken()));
    }

}
