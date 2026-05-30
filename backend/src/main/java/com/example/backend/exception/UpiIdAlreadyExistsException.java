package com.example.backend.exception;

import org.springframework.http.HttpStatus;

public class UpiIdAlreadyExistsException extends BusinessException {

    public UpiIdAlreadyExistsException() {
        super("The provided UPI ID is already associated with another account. Please choose a different UPI ID.", HttpStatus.CONFLICT);
    }

    public UpiIdAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
