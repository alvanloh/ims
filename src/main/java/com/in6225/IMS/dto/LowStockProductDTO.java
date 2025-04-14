// src/main/java/com/in6225/IMS/dto/LowStockProductDTO.java
package com.in6225.IMS.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data               // Lombok: Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor  // Lombok: Generates no-args constructor
@AllArgsConstructor // Lombok: Generates all-args constructor (optional, but can be useful)
public class LowStockProductDTO {
    private Long id;
    private String name;
    private String sku;
    private int quantity;
    private int reorder;
}