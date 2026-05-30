package com.example.backend.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiError(
        LocalDateTime timeStamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> validationErrors // Field-specific validation messages (null if not a validation error)
) {

    public ApiError(int status,
            String error,
            String message,
            String path) {

        this(LocalDateTime.now(), status, error, message, path, null);
    }

    public ApiError(int status, String error, String message, String path, Map<String, String> validationErrors) {
        this(LocalDateTime.now(), status, error, message, path, validationErrors);
    }
}