// src/main/java/com/in6225/IMS/repository/projection/MonthlyTransactionQueryResult.java
package com.in6225.IMS.repository.projection;

import com.in6225.IMS.entity.Transaction;

public interface MonthlyTransactionQueryResult {
    Integer getYear();
    Integer getMonth();
    Transaction.TransactionType getTransactionType(); // Or use String getTransactionType();
    Long getTotalQuantity();
}