package com.example.backend.controller;

import com.example.backend.entity.Transaction;
import com.example.backend.exception.ApiResponse;
import com.example.backend.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Transaction>> sendMoney(@Valid @RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.sendMoney(transaction);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Transaction recorded successfully", savedTransaction),
                HttpStatus.CREATED
        );
    }
}