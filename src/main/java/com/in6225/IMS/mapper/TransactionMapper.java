package com.in6225.IMS.mapper;

import com.in6225.IMS.dto.TransactionDTO;
import com.in6225.IMS.entity.Product;
import com.in6225.IMS.entity.Transaction;
import com.in6225.IMS.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {

    // Convert Transaction entity to TransactionDTO
    public TransactionDTO toTransactionDTO(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setProductId(transaction.getProduct().getId());
        transactionDTO.setProductName(transaction.getProduct().getName());
        transactionDTO.setProductSku(transaction.getProduct().getSku());
        transactionDTO.setQuantity(transaction.getQuantity());
        transactionDTO.setTransactionType(transaction.getTransactionType().name());
        transactionDTO.setReference(transaction.getReference());
        transactionDTO.setTransactionDate(transaction.getTransactionDate());
        transactionDTO.setCreatedBy(transaction.getCreatedBy());
        return transactionDTO;
    }

    // Convert TransactionDTO to Transaction entity
    public Transaction toTransactionEntity(TransactionDTO transactionDTO) {
        if (transactionDTO == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setId(transactionDTO.getId());
        transaction.setQuantity(transactionDTO.getQuantity());
        transaction.setTransactionType(Transaction.TransactionType.valueOf(transactionDTO.getTransactionType()));
        transaction.setReference(transactionDTO.getReference());
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        return transaction;
    }

    // Convert a list of Transaction entities to a list of TransactionDTOs
    public List<TransactionDTO> toTransactionDTOList(List<Transaction> transactions) {
        return transactions.stream()
                .map(this::toTransactionDTO)
                .collect(Collectors.toList());
    }
}

