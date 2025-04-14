package com.in6225.IMS.repository;

import com.in6225.IMS.entity.Alert;
import com.in6225.IMS.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    // Find unresolved alerts for a product
    Optional<Alert> findByProductAndResolved(Product product, boolean resolved);

    // Find all unresolved alerts
    List<Alert> findByResolvedFalse();

    // Find all alerts for a specific product
    List<Alert> findByProduct(Product product);

    // Find resolved alerts if needed
    List<Alert> findByResolvedTrue();
}
