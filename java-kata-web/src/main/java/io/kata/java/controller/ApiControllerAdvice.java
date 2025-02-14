package io.kata.java.controller;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.kata.java.error.ErrorType;
import io.kata.java.error.JavaKataException;
import io.kata.java.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(assignableTypes = RestController.class)
public class ApiControllerAdvice {

	@ExceptionHandler(JavaKataException.class)
	public ResponseEntity<ApiResponse<?>> handleJavaKataException(JavaKataException exception) {
		switch (exception.getErrorType().getLogLevel()) {
			case ERROR -> log.error("CoreException : {}", exception.getMessage(), exception);
			case WARN -> log.warn("CoreException : {}", exception.getMessage(), exception);
			default -> log.info("CoreException : {}", exception.getMessage(), exception);
		}

		return new ResponseEntity<>(
			ApiResponse.error(
				exception.getErrorType(),
				exception.getData()),
			exception.getErrorType().getStatus()
		);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException exception) {
		// 검증 실패 항목을 Map의 key-value 형식으로 수집
		Map<String, String> validationErrors = exception.getBindingResult()
			.getFieldErrors()
			.stream()
			.collect(Collectors.toMap(
				FieldError::getField,         // 필드 이름(key)
				DefaultMessageSourceResolvable::getDefaultMessage // 해당 검증 오류 메시지(value)
			));

		return new ResponseEntity<>(ApiResponse.error(ErrorType.VALIDATION_ERROR, validationErrors),
			ErrorType.VALIDATION_ERROR.getStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<?>> handleException(Exception exception) {
		log.error("Exception : {}", exception.getMessage(), exception);
		return new ResponseEntity<>(ApiResponse.error(ErrorType.DEFAULT_ERROR), ErrorType.DEFAULT_ERROR.getStatus());
	}
}