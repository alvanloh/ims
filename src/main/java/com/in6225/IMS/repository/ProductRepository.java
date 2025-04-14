package com.in6225.IMS.repository;

import com.in6225.IMS.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Find a product by its SKU (unique identifier)
    Optional<Product> findBySku(String sku);

    // Find a product by its name
    Optional<Product> findByName(String name);
}

