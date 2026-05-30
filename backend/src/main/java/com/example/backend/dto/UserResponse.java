package com.example.backend.dto;

public record UserResponse(
        Long id,
        String fullName,
        String email,
        String role,
        boolean isActive,
        boolean isDeleted,
        String token
) {
}
