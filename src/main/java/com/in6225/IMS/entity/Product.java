// src/main/java/com/in6225/IMS/entity/Product.java
package com.in6225.IMS.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product extends BaseEntity { // Assuming BaseEntity has @MappedSuperclass and ID generation

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false, unique = true)
    private String sku;  // Stock Keeping Unit

    @Column(nullable = false)
    private String category;  // Category of the product

    @Column(nullable = false)
    private int reorder;

    // Bidirectional relationship with Transaction entity
    // WILL ALSO DELETE all associated Transaction records.
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) // Added FetchType.LAZY
    @JsonManagedReference
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) // Added FetchType.LAZY
    @JsonManagedReference
    private List<Alert> alerts;
}