package com.example.backend.controller;

import com.example.backend.entity.User;
import com.example.backend.exception.ApiResponse;
import com.example.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class PayFlowUserController {
    private final UserService userService;

    public PayFlowUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllPayFlowUsers();
        return ResponseEntity.ok(new ApiResponse<>(true, "Users fetched successfully", users));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        User user = userService.getPayFlowUserById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "User fetched successfully", user));
    }

    @GetMapping("upi/{upiId}")
    public ResponseEntity<ApiResponse<User>> getUserByUpiId(@PathVariable String upiId) {
        User user = userService.findPayFlowUserByUpiId(upiId);
        return ResponseEntity.ok(new ApiResponse<>(true, "User fetched successfully", user));
    }

    @GetMapping("balance-above/{amount}")
    public ResponseEntity<ApiResponse<List<User>>> getUsersWithBalanceAbove(@PathVariable BigDecimal amount) {
        List<User> users = userService.findPayFlowUsersWithBalanceGreaterThan(amount);
        return ResponseEntity.ok(new ApiResponse<>(true, "Users fetched successfully", users));
    }
}
