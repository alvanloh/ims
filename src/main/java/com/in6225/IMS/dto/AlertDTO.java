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
public class AlertDTO {

    private Long id;

    @NotNull(message = "Product ID must not be null")
    @Positive(message = "Product ID must be a positive number")
    private Long productId;

    @Pattern(regexp = "^[A-Za-z0-9-]{3,50}$", message = "Product SKU must be alphanumeric and between 3-50 characters")
    private String productSku;

    @Size(min = 1, max = 255, message = "Product name must be between 1 and 255 characters")
    private String productName;

    @NotNull(message = "Resolved status must be specified")
    private boolean resolved;

    @PastOrPresent(message = "Created date cannot be in the future")
    private LocalDateTime createdDate;

    @PastOrPresent(message = "Updated date cannot be in the future")
    private LocalDateTime updatedDate;

    @NotBlank(message = "Created by cannot be empty")
    @Size(min = 3, max = 50, message = "Created by must be between 3 and 50 characters")
    @Pattern(regexp = "^[A-Za-z0-9_-]*$", message = "Created by must contain only letters, numbers, underscores and hyphens")
    private String createdBy;

    @Size(min = 3, max = 50, message = "Updated by must be between 3 and 50 characters")
    @Pattern(regexp = "^[A-Za-z0-9_-]*$", message = "Updated by must contain only letters, numbers, underscores and hyphens")
    private String updatedBy;
}
