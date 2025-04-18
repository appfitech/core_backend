package com.fitech.app.commons.application.exceptions;

import com.fitech.app.users.application.dto.ErrorResponseDto;
import com.fitech.app.users.application.exception.DuplicatedUserException;
import com.fitech.app.users.application.exception.UserNotFoundException;
import com.fitech.app.users.application.exception.InvalidPasswordException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Environment environment;

    public GlobalExceptionHandler(Environment environment) {
        this.environment = environment;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        log.error("User not found: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getDescription(false), ex);
    }

    @ExceptionHandler(DuplicatedUserException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicatedUserException(DuplicatedUserException ex, WebRequest request) {
        log.error("Duplicated user: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request.getDescription(false), ex);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidPasswordException(InvalidPasswordException ex, WebRequest request) {
        log.error("Invalid credentials: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), request.getDescription(false), ex);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(ValidationException ex, WebRequest request) {
        log.error("Validation error: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getDescription(false), ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception ex, WebRequest request) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, 
                "An unexpected error occurred", 
                request.getDescription(false),
                ex);
    }

    private ResponseEntity<ErrorResponseDto> buildErrorResponse(HttpStatus status, String message, String path, Exception ex) {
        ErrorResponseDto.ErrorResponseDtoBuilder builder = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path);

        // Add stacktrace in development profile
        if (isDevelopmentProfile()) {
            builder.stackTrace(Arrays.toString(ex.getStackTrace()));
        }

        return new ResponseEntity<>(builder.build(), status);
    }

    private boolean isDevelopmentProfile() {
        return !Arrays.asList(environment.getActiveProfiles()).contains("prod");
    }
}