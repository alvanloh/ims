// src/main/java/com/in6225/IMS/dto/ProductDTO.java
package com.in6225.IMS.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id; // Nullable for creation, non-null for update/response

    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity; // Use Integer wrapper class to allow @NotNull

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", message = "Price cannot be negative")
    @Digits(integer = 10, fraction = 2, message = "Price format is invalid (max 10 integer digits, 2 fraction digits)")
    private Double price; // Use Double wrapper class

    @NotBlank(message = "SKU cannot be blank")
    @Size(min = 3, max = 50, message = "SKU must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "SKU can only contain letters, numbers, underscores, and hyphens")
    private String sku;  // Stock Keeping Unit

    @NotBlank(message = "Category cannot be blank")
    @Size(min = 2, max = 50, message = "Category must be between 2 and 50 characters")
    private String category;

    @NotNull(message = "Reorder level cannot be null")
    @Min(value = 0, message = "Reorder level cannot be negative")
    private Integer reorder;
    }