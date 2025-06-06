package io.javakata.core.support.aop;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.javakata.core.support.error.ErrorType;
import io.javakata.core.support.error.JavaKataException;
import io.javakata.core.support.response.ApiResponse;
import io.javakata.storage.db.core.error.ConflictException;
import io.javakata.storage.db.core.error.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(JavaKataException.class)
    public ResponseEntity<ApiResponse<?>> handleJavaKataException(JavaKataException exception) {
        switch (exception.getErrorType().getLogLevel()) {
            case ERROR -> log.error("JavaKataException : {}", exception.getMessage(), exception);
            case WARN -> log.warn("JavaKataException : {}", exception.getMessage(), exception);
            default -> log.info("JavaKataException : {}", exception.getMessage(), exception);
        }

        return new ResponseEntity<>(ApiResponse.error(exception.getErrorType(), exception.getData()),
                exception.getErrorType().getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        // 검증 실패 항목을 Map의 key-value 형식으로 수집
        Map<String, String> validationErrors = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(FieldError::getField, // 필드 이름(key)
                    DefaultMessageSourceResolvable::getDefaultMessage
            // 해당 검증 오류 메시지(value) TODO 해당키에 Validation이 2개 이상일 경우 예외 발생함.
            ));

        return new ResponseEntity<>(ApiResponse.error(ErrorType.VALIDATION_ERROR, validationErrors),
                ErrorType.VALIDATION_ERROR.getStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFoundException(NotFoundException exception) {
        return new ResponseEntity<>(ApiResponse.error(ErrorType.NOT_FOUND_ERROR, exception.getMessage()),
                ErrorType.NOT_FOUND_ERROR.getStatus());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFoundException(ConflictException exception) {
        return new ResponseEntity<>(ApiResponse.error(ErrorType.CONFLICT_ERROR, exception.getMessage()),
                ErrorType.CONFLICT_ERROR.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception exception) {
        log.error("Exception : {}", exception.getMessage(), exception);
        return new ResponseEntity<>(ApiResponse.error(ErrorType.DEFAULT_ERROR), ErrorType.DEFAULT_ERROR.getStatus());
    }

}