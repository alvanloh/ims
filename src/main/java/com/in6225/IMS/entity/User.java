package com.in6225.IMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    private String role; // e.g., "ADMIN", "MANAGER"
}
