package com.practice.blog.exceptions;

import com.practice.blog.dtos.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        return buildResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        log.warn("Validation failed: {}", errors);
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation Error", "Invalid request parameters", errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String rootCause = extractRootCause(ex);
        log.error("Data integrity violation: {}", rootCause, ex);

        return buildResponse(HttpStatus.CONFLICT, "Data Integrity Violation",
                "Database constraint violated. " + rootCause, null);
    }

//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex) {
//        log.warn("Access denied: {}", ex.getMessage());
//        return buildResponse(HttpStatus.FORBIDDEN, "Forbidden",
//                "You do not have permission to perform this action.", null);
//    }
//
//    @ExceptionHandler({ BadCredentialsException.class, UsernameNotFoundException.class })
//    public ResponseEntity<ApiError> handleAuthenticationErrors(Exception ex) {
//        log.warn("Authentication failed: {}", ex.getMessage());
//        return buildResponse(HttpStatus.UNAUTHORIZED, "Unauthorized",
//                "Invalid username or password.", null);
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
                ex.getMessage(), null);
    }

    private ResponseEntity<ApiError> buildResponse(HttpStatus status, String error, String message, Map<String, String> details) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(error)
                .message(message)
                .details(details)
                .build();

        return new ResponseEntity<>(apiError, status);
    }

    private String extractRootCause(Throwable ex) {
        Throwable root = ex;
        while (root.getCause() != null && root.getCause() != root) {
            root = root.getCause();
        }
        return root.getMessage();
    }
}
