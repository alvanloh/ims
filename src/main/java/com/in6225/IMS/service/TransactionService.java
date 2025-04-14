package com.in6225.IMS.service;

import com.in6225.IMS.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {

    // Method to create a new transaction
    TransactionDTO createTransaction(TransactionDTO transactionDTO);

    // Method to update an existing transaction
    TransactionDTO updateTransaction(Long transactionId, TransactionDTO transactionDTO);

    // Method to get a transaction by its ID
    TransactionDTO getTransactionById(Long transactionId);

    // Method to get all transactions
    List<TransactionDTO> getAllTransactions();

    // Method to delete a transaction by its ID
    void deleteTransaction(Long transactionId);
}

