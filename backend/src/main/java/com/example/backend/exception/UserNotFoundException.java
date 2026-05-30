package com.example.backend.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BusinessException{

    public UserNotFoundException(String email) {
        super("Sorry but no user found with the email: " + email,  HttpStatus.NOT_FOUND);
    }

    public UserNotFoundException() {
        super("The requested user account could not be found.",  HttpStatus.NOT_FOUND);
    }
}
