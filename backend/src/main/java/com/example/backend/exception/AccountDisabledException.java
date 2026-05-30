package com.example.backend.exception;

import org.springframework.http.HttpStatus;

public class AccountDisabledException extends BusinessException{

    public AccountDisabledException() {
        super("Your account has been deactivated. Please contact support to reactivate your account.", HttpStatus.FORBIDDEN);
    }

    public AccountDisabledException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
