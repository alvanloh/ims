package com.in6225.IMS.dto;

import jakarta.validation.constraints.*;
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
    
    @NotNull(message = "Product ID or SKU must be provided")
    private Long productId;
    
    @Pattern(regexp = "^[A-Za-z0-9-]{3,50}$", message = "Product SKU must be alphanumeric and between 3-50 characters")
    private String productSku;
    
    @Size(min = 1, max = 255, message = "Product name must be between 1 and 255 characters")
    private String productName;
    
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;
    
    @NotBlank(message = "Transaction type cannot be empty")
    @Pattern(regexp = "^(IN|OUT|ADJUSTMENT)$", message = "Transaction type must be either IN, OUT, or ADJUSTMENT")
    private String transactionType;
    
    @Size(max = 255, message = "Reference cannot exceed 255 characters")
    @Pattern(regexp = "^[A-Za-z0-9-_#\\s]*$", message = "Reference must contain only letters, numbers, spaces, and common symbols")
    private String reference;
    
    @PastOrPresent(message = "Transaction date cannot be in the future")
    private LocalDateTime transactionDate;
    
    @NotBlank(message = "Created by cannot be empty")
    @Size(min = 3, max = 50, message = "Created by must be between 3 and 50 characters")
    @Pattern(regexp = "^[A-Za-z0-9_-]*$", message = "Created by must contain only letters, numbers, underscores and hyphens")
    private String createdBy;
}
