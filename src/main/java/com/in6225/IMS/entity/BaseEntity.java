package com.in6225.IMS.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    // Hook method to populate createdDate, updatedDate, createdBy, and updatedBy before persist
    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
        updatedDate = createdDate;
        createdBy = getCurrentUser();  // Fetch the currently authenticated user for creation
        updatedBy = createdBy;        // Initially set updatedBy to the same value as createdBy
    }

    // Hook method to update updatedDate and updatedBy before update
    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDateTime.now();
        updatedBy = getCurrentUser();  // Fetch the currently authenticated user for update
    }

    // Method to get the current authenticated user (for example using Spring Security)
    private String getCurrentUser() {
        // Replace with your actual logic to fetch the authenticated user
        // This could be something like SecurityContextHolder.getContext().getAuthentication().getName() in Spring Security
        return "system";  // Placeholder, replace with real logic
    }
}