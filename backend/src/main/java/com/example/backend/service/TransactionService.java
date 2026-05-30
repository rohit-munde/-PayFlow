package com.example.backend.service;

import com.example.backend.entity.Transaction;
import com.example.backend.exception.InvalidTransactionException;
import com.example.backend.exception.UpiIdNotFoundException;
import com.example.backend.repository.TransactionRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public Transaction sendMoney(Transaction transaction) {
        if (transaction.getSenderUpiId().equals(transaction.getReceiverUpiId())) {
            throw new InvalidTransactionException("Sender and receiver UPI ID cannot be same");
        }

        userRepository.findByUpiId(transaction.getSenderUpiId())
                .orElseThrow(() -> new UpiIdNotFoundException(transaction.getSenderUpiId()));

        userRepository.findByUpiId(transaction.getReceiverUpiId())
                .orElseThrow(() -> new UpiIdNotFoundException(transaction.getReceiverUpiId()));

        return transactionRepository.save(transaction);
    }
}
