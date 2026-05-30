package com.example.backend.exception;

import org.springframework.http.HttpStatus;

public class UpiIdNotFoundException extends BusinessException {

    public UpiIdNotFoundException() {
        super("The provided UPI ID was not found. Please check the UPI ID and try again.", HttpStatus.NOT_FOUND);
    }

    public UpiIdNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
