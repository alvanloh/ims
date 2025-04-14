package com.in6225.IMS.repository;

import com.in6225.IMS.entity.Transaction;
import com.in6225.IMS.entity.Product;
import com.in6225.IMS.repository.projection.MonthlyTransactionQueryResult;
import org.springframework.data.domain.Page; // Import Page
import org.springframework.data.domain.Pageable; // Import Pageable
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Find a transaction by its reference (e.g., order ID, invoice number)
    Optional<Transaction> findByReference(String reference);

    // Find all transactions for a specific product
    List<Transaction> findByProduct(Product product);

    // Find all transactions of a specific type (IN, OUT, ADJUSTMENT)
    List<Transaction> findByTransactionType(Transaction.TransactionType transactionType);

    // Find all transactions for a specific product within a date range
    List<Transaction> findByProductAndTransactionDateBetween(Product product, LocalDateTime startDate, LocalDateTime endDate);

    Page<Transaction> findByOrderByTransactionDateDesc(Pageable pageable);

    @Query("SELECT t FROM Transaction t JOIN FETCH t.product ORDER BY t.transactionDate DESC")
    Page<Transaction> findRecentTransactionsWithProduct(Pageable pageable); // Keep this for recent tx

    // *** Ensure this method exists for Monthly Summary ***
    @Query("SELECT " +
            "   FUNCTION('YEAR', t.transactionDate) as year, " +
            "   FUNCTION('MONTH', t.transactionDate) as month, " +
            "   t.transactionType as transactionType, " +
            "   SUM(t.quantity) as totalQuantity " +
            "FROM Transaction t " +
            "WHERE t.transactionDate >= :startDate " +
            "  AND t.transactionType IN ('IN', 'OUT') " + // Filter relevant types
            "GROUP BY year, month, t.transactionType " +
            "ORDER BY year, month, t.transactionType") // Ordering helps slightly, but TreeMap ensures final sort
    List<MonthlyTransactionQueryResult> findMonthlyTransactionSummary(
            @Param("startDate") LocalDateTime startDate);
}