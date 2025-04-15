package com.in6225.IMS.repository;

import com.in6225.IMS.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Find all products ordered by ID in descending order
    List<Product> findAllByOrderByIdDesc();

    Optional<Product> findBySku(String sku);

    @Query("SELECT COUNT(p) FROM Product p")
    Long countTotalProducts();

    @Query("SELECT COUNT(p) FROM Product p WHERE p.quantity <= p.reorder")
    Long countLowStockProducts();

    @Query("SELECT COUNT(p) FROM Product p WHERE p.quantity = 0")
    Long countOutOfStockProducts();

    // Implement LowStockProducts by retrieving full Product entities with low stock.
    @Query("SELECT p FROM Product p WHERE p.quantity <= p.reorder")
    List<Product> LowStockProducts();

}
