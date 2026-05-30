package com.example.backend.dto;

import java.math.BigDecimal;

public record UserResponse(
        Long id,
        String fullName,
        String email,
        String role,
        String upiId,
        String phoneNumber,
        BigDecimal balance,
        boolean isActive,
        boolean isDeleted,
        String token
) {
}