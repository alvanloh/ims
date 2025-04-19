package com.in6225.IMS.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference
    private Product product;  // Foreign key to Product

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType; // IN, OUT, ADJUSTMENT

    @Column(nullable = false)
    @PastOrPresent(message = "Transaction date cannot be in the future")
    private LocalDateTime transactionDate = LocalDateTime.now();

    @Column(nullable = false)
    private String reference;  // Reference for the transaction (e.g., order ID)

    public enum TransactionType {
        IN, OUT, ADJUSTMENT
    }
}
