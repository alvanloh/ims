// src/main/java/com/in6225/IMS/dto/RecentTransactionDTO.java
package com.in6225.IMS.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentTransactionDTO {
    private Long id;
    private String productName; // Changed from productId for better display
    private String productSku;  // Added SKU for identification
    private int quantity;
    private String transactionType; // String representation of the Enum
    private LocalDateTime transactionDate;
    private String reference;
}