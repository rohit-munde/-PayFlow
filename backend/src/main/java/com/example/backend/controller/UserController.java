package com.example.backend.controller;

import com.example.backend.dto.*;
import com.example.backend.entity.User;
import com.example.backend.exception.ApiResponse;
import com.example.backend.service.UserService;
import com.example.backend.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public UserController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody UserDto user) {
        User createdUser = this.userService.createUser(user);
        return new ResponseEntity<>(new ApiResponse<>(true, "User created successfully", createdUser), HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<UserResponse>> login(@Valid @RequestBody UserLoginDto userLoginDto) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken
                .unauthenticated(userLoginDto.getEmail(), userLoginDto.getPassword());
        authenticationManager.authenticate(authenticationRequest);

        User user = userService.getUserByEmail(userLoginDto.getEmail());

        String token = jwtService.generateToken(user.getEmail());

        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                user.isDeleted(),
                token);

        return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", userResponse));
    }

    @GetMapping("me")
    public ResponseEntity<ApiResponse<UserResponseDto>> getCurrentUser() {
        return ResponseEntity.ok(new ApiResponse<>(true, "User retrieved successfully", userService.getCurrentUser()));
    }

    @GetMapping("dashboard")
    public ResponseEntity<ApiResponse<DashboardResponse>> dashboard() {
        DashboardResponse res = new DashboardResponse();
        res.setDummyStr("This is a protected dashboard endpoint. Only authenticated users can see this.");
        return ResponseEntity.ok(new ApiResponse<>(true, "Dashboard retrieved successfully", res));
    }
}
