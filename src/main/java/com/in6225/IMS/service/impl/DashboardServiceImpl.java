package com.in6225.IMS.service.impl;

import com.in6225.IMS.dto.MonthlyTransactionDTO;
import com.in6225.IMS.repository.AlertRepository;
import com.in6225.IMS.repository.ProductRepository;
import com.in6225.IMS.repository.TransactionRepository;
import com.in6225.IMS.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ProductRepository productRepository;
    private final AlertRepository alertRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public DashboardServiceImpl(ProductRepository productRepository, AlertRepository alertRepository, TransactionRepository transactionRepository) {
        this.productRepository = productRepository;
        this.alertRepository = alertRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Long getTotalProducts() {
        return productRepository.countTotalProducts();
    }

    @Override
    public Long getTotalAlertsYTD() {
        return alertRepository.countTotalAlertsYTD();
    }

    @Override
    public Long getTotalLowStockProducts() {
        return productRepository.countLowStockProducts();
    }

    @Override
    public Long getTotalOutOfStockProducts() {
        return productRepository.countOutOfStockProducts();
    }
    
    public List<MonthlyTransactionDTO> getMonthlyInOutSums() {
        LocalDateTime startDate = LocalDateTime.now().minusMonths(12);
        List<Object[]> results = transactionRepository.findMonthlyInOutSums(startDate);
        return results.stream()
                .map(o -> new MonthlyTransactionDTO((String) o[0],
                        ((Number) o[1]).longValue(),
                        ((Number) o[2]).longValue()))
                .collect(Collectors.toList());
    }
    
}
