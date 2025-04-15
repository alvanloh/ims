package com.in6225.IMS.repository;

import com.in6225.IMS.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Find all transactions by ID in descending order
    List<Transaction> findAllByOrderByIdDesc();

    // Find sum of quantities for transaction types 'IN' and 'OUT' grouped by month for the last 12 months
    @Query("SELECT FUNCTION('DATE_FORMAT', t.transactionDate, '%Y-%m') as month, " +
            "SUM(CASE WHEN t.transactionType = 'IN' THEN t.quantity ELSE 0 END) as inSum, " +
            "SUM(CASE WHEN t.transactionType = 'OUT' THEN t.quantity ELSE 0 END) as outSum " +
            "FROM Transaction t " +
            "WHERE t.transactionDate >= :startDate " +
            "GROUP BY FUNCTION('DATE_FORMAT', t.transactionDate, '%Y-%m') " +
            "ORDER BY month")
    List<Object[]> findMonthlyInOutSums(@Param("startDate") LocalDateTime startDate);

    // Find all transactions by product ID in descending order by ID
    List<Transaction> findAllByProductIdOrderByIdDesc(Long productId);

    // Find all transactions that occurred today
    @Query("SELECT t FROM Transaction t WHERE t.transactionDate BETWEEN :startOfDay AND :endOfDay ORDER BY t.id DESC")
    List<Transaction> findTodaysTransactions(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );
}
