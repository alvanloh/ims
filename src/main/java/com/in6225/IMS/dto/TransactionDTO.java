package com.in6225.IMS.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Long id;
    private Long productId;  // The ID of the associated product
    private String productSku;
    private String productName;
    private int quantity;
    private String transactionType;  // IN, OUT, or ADJUSTMENT
    private String reference;  // Reference like order ID, invoice number
    private LocalDateTime transactionDate;
    private String createdBy;
}