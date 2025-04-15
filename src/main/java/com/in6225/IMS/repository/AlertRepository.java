package com.in6225.IMS.repository;

import com.in6225.IMS.entity.Alert;
import com.in6225.IMS.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    // Find all alerts ordered by ID in descending order
    List<Alert> findAllByOrderByIdDesc();

    @Query("SELECT COUNT(a) FROM Alert a WHERE YEAR(a.createdDate) = YEAR(CURRENT_DATE)")
    Long countTotalAlertsYTD();
}
