// src/main/java/com/in6225/IMS/dto/MonthlyTransactionSummaryDTO.java
package com.in6225.IMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyTransactionSummaryDTO {
    private int year;
    private int month; // 1-12
    private String monthLabel; // e.g., "2023-11" for easier display
    private long totalInQuantity;
    private long totalOutQuantity;
}