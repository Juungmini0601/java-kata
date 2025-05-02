package io.javakata.core.submission.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.javakata.core.config.aop.Auth;
import io.javakata.core.submission.api.request.SubmitRequest;
import io.javakata.core.submission.application.SubmissionService;
import io.javakata.core.support.response.ApiResponse;
import io.javakata.model.user.CurrentUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping("/api/v1/problems/{problemId}/submit")
    public ApiResponse<?> submit(@PathVariable Long problemId, @Valid @RequestBody SubmitRequest request,
            @Auth CurrentUser currentUser) {
        final String email = currentUser.getEmail();
        SubmissionService.Command command = new SubmissionService.Command(request.language(), request.code());
        submissionService.submit(problemId, command, email);

        return ApiResponse.success();
    }

}