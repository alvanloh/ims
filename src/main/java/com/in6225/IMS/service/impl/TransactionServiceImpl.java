package com.in6225.IMS.service.impl;

import com.in6225.IMS.dto.TransactionDTO;
import com.in6225.IMS.entity.Alert;
import com.in6225.IMS.entity.Product;
import com.in6225.IMS.entity.Transaction;
import com.in6225.IMS.mapper.TransactionMapper;
import com.in6225.IMS.repository.AlertRepository;
import com.in6225.IMS.repository.ProductRepository;
import com.in6225.IMS.repository.TransactionRepository;
import com.in6225.IMS.service.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;  // Inject the ProductRepository
    private final TransactionMapper transactionMapper;
    private final AlertRepository alertRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, ProductRepository productRepository, TransactionMapper transactionMapper, AlertRepository alertRepository) {
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
        this.transactionMapper = transactionMapper;
        this.alertRepository = alertRepository;
    }

    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        // Retrieve the product to update its quantity
        Product product = productRepository.findBySku(transactionDTO.getProductSku())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Convert DTO to Entity
        Transaction transaction = transactionMapper.toTransactionEntity(transactionDTO);
        transaction.setProduct(product);

        // Store the current quantity before update
        int currentQuantity = product.getQuantity();

        // Perform validation before updating quantity
        if (transactionDTO.getTransactionType().equals("OUT") &&
                currentQuantity - transactionDTO.getQuantity() < 0) {
            throw new RuntimeException("Transaction would result in negative product quantity");
        }

        // Update the product quantity based on the transaction type
        updateProductQuantity(product, transactionDTO);

        // Get the updated quantity after the update operation
        int updatedQuantity = product.getQuantity();

        // Check if we need to create a reorder alert
        if (currentQuantity > product.getReorder() && updatedQuantity < product.getReorder()) {
            createReorderAlert(product);
        }

        // Save the transaction
        transaction = transactionRepository.save(transaction);

        // Return the saved transaction as DTO
        return transactionMapper.toTransactionDTO(transaction);
    }

    // Method to update the product's quantity based on the transaction type
    private void updateProductQuantity(Product product, TransactionDTO transactionDTO) {
        int currentQuantity = product.getQuantity();
        int quantityChange = transactionDTO.getQuantity();

        switch (transactionDTO.getTransactionType()) {
            case "IN":
                // Add quantity to product for "IN" transaction
                product.setQuantity(currentQuantity + quantityChange);
                break;
            case "OUT":
                // Subtract quantity from product for "OUT" transaction
                product.setQuantity(currentQuantity - quantityChange);
                break;
            case "ADJUSTMENT":
                // Set quantity directly for "ADJUSTMENT" transaction
                product.setQuantity(quantityChange);
                break;
            default:
                throw new IllegalArgumentException("Invalid transaction type");
        }

        // Save updated product to the database
        productRepository.save(product);
    }

    @Override
    public TransactionDTO updateTransaction(Long transactionId, TransactionDTO transactionDTO) {
        Transaction existingTransaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        
        // Find product by SKU if it's provided in the DTO
        if (transactionDTO.getProductSku() != null) {
            Product product = productRepository.findBySku(transactionDTO.getProductSku())
                    .orElseThrow(() -> new RuntimeException("Product not found with SKU: " + transactionDTO.getProductSku()));
            existingTransaction.setProduct(product);
        }

        existingTransaction.setQuantity(transactionDTO.getQuantity());
        existingTransaction.setTransactionType(Transaction.TransactionType.valueOf(transactionDTO.getTransactionType()));
        existingTransaction.setReference(transactionDTO.getReference());
        existingTransaction.setTransactionDate(transactionDTO.getTransactionDate());

        existingTransaction = transactionRepository.save(existingTransaction);
        return transactionMapper.toTransactionDTO(existingTransaction);
    }

    @Override
    public TransactionDTO getTransactionById(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new RuntimeException("Transaction not found"));
        return transactionMapper.toTransactionDTO(transaction);
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAllByOrderByIdDesc();
        return transactionMapper.toTransactionDTOList(transactions);
    }

    @Override
    public List<TransactionDTO> getAllTransactionsByProductId(Long productId) {
        List<Transaction> transactions = transactionRepository.findAllByProductIdOrderByIdDesc(productId);
        return transactionMapper.toTransactionDTOList(transactions);
    }

    public List<TransactionDTO> getTodaysTransactions() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        List<Transaction> transactions = transactionRepository.findTodaysTransactions(startOfDay, endOfDay);
        return transactionMapper.toTransactionDTOList(transactions);
    }

    @Override
    public void deleteTransaction (Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new RuntimeException("Transaction not found"));
        transactionRepository.delete(transaction);
    }

    private void createReorderAlert(Product product) {
        Alert alert = new Alert();
        alert.setProduct(product);
        alertRepository.save(alert);
    }
}
