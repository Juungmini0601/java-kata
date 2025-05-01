package io.javakata.core.user.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.javakata.core.support.response.ApiResponse;
import io.javakata.core.user.api.request.CreateUserRequest;
import io.javakata.core.user.api.response.UserResponse;
import io.javakata.core.user.application.UserService;
import io.javakata.model.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/v1/users")
    public ApiResponse<UserResponse> register(@RequestBody @Valid CreateUserRequest request) {
        User registeredUser = userService.register(request.email(), request.password(), request.nickname());

        return ApiResponse.success(UserResponse.from(registeredUser));
    }

}
