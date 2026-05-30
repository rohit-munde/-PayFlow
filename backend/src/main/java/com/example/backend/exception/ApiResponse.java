package com.example.backend.exception;

public record ApiResponse<T>(
    boolean success,
    String message,
    T data
) {
    // Convenience constructor for returning only data
    public ApiResponse(T data) {
        this(true, "Operation completed successfully", data);
    }

    // Convenience constructor for returning a custom message and data
    public ApiResponse(String message, T data) {
        this(true, message, data);
    }
}
