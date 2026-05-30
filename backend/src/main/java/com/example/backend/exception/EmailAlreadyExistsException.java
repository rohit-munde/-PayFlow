package com.example.backend.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends BusinessException {

    public EmailAlreadyExistsException() {
        super("This email address is already registered. If this is you, please try logging in or resetting your password.",  HttpStatus.CONFLICT);
    }

    public EmailAlreadyExistsException(String message) {
        super(message,  HttpStatus.CONFLICT);
    }

}
