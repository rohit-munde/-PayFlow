package com.example.backend.exception;

import org.springframework.http.HttpStatus;

public class InvalidTransactionException extends BusinessException {

    public InvalidTransactionException() {
        super("The transaction is invalid. Please check the transaction details and try again.", HttpStatus.BAD_REQUEST);
    }

    public InvalidTransactionException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
