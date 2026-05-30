package com.example.backend.dto;

public record UserResponseDto(
        Long id,
        String name,
        String email,
        String role,
        Boolean isActive
) {
}
