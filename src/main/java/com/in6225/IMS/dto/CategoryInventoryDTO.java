// src/main/java/com/in6225/IMS/dto/CategoryInventoryDTO.java
package com.in6225.IMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInventoryDTO {
    private String categoryName;
    private long totalQuantity;
}