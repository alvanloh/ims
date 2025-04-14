// src/main/java/com/in6225/IMS/service/impl/DashboardServiceImpl.java
package com.in6225.IMS.service.impl;

import com.in6225.IMS.dto.*;
import com.in6225.IMS.entity.Product;
import com.in6225.IMS.entity.Transaction;
import com.in6225.IMS.repository.ProductRepository;
import com.in6225.IMS.repository.TransactionRepository;
import com.in6225.IMS.repository.projection.MonthlyTransactionQueryResult; // Import projection
import com.in6225.IMS.service.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private static final Logger log = LoggerFactory.getLogger(DashboardServiceImpl.class);
    private static final int MONTHS_FOR_SUMMARY = 6;

    private final ProductRepository productRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public DashboardServiceImpl(ProductRepository productRepository, TransactionRepository transactionRepository) {
        this.productRepository = productRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardData getDashboardData(int recentTransactionLimit) {
        log.info("Fetching dashboard data. Recent Tx Limit: {}, Monthly Summary Months: {}", recentTransactionLimit, MONTHS_FOR_SUMMARY);

        DashboardData data = new DashboardData();
        List<Product> allProducts = productRepository.findAll(); // Re-evaluate for large scale

        calculateBasicSummaries(data, allProducts);
        mapLowStockProducts(data, allProducts);
        mapRecentTransactions(data, recentTransactionLimit);
        calculateCategoryInventorySummary(data, allProducts);

        // --- Ensure this calls the DB query version ---
        calculateMonthlyTransactionSummary(data);

        log.info("Dashboard data fetched successfully.");
        return data;
    }

    // --- Helper Methods ---

    private void calculateBasicSummaries(DashboardData data, List<Product> allProducts) {
        // ... (implementation as before) ...
        data.setTotalProductCount(allProducts.size());
        long totalQuantity = 0;
        double totalValue = 0.0;
        for (Product product : allProducts) {
            totalQuantity += product.getQuantity();
            totalValue += (product.getQuantity() * product.getPrice());
        }
        data.setTotalInventoryQuantity(totalQuantity);
        data.setTotalInventoryValue(totalValue);
    }

    private void mapLowStockProducts(DashboardData data, List<Product> allProducts) {
        // ... (implementation as before) ...
        List<LowStockProductDTO> lowStockProductDTOs = allProducts.stream()
                .filter(p -> p.getQuantity() <= p.getReorder())
                .map(this::mapToLowStockProductDTO)
                .collect(Collectors.toList());
        data.setLowStockProductCount(lowStockProductDTOs.size());
        data.setLowStockProducts(lowStockProductDTOs);
    }

    private void mapRecentTransactions(DashboardData data, int recentTransactionLimit) {
        // ... (implementation as before, preferably using findRecentTransactionsWithProduct) ...
        if (recentTransactionLimit > 0) {
            Pageable limit = PageRequest.of(0, recentTransactionLimit, Sort.by(Sort.Direction.DESC, "transactionDate"));
            Page<Transaction> recentTransactionsPage = transactionRepository.findRecentTransactionsWithProduct(limit);
            List<RecentTransactionDTO> recentTransactionDTOs = recentTransactionsPage.getContent().stream()
                    .map(this::mapToRecentTransactionDTO)
                    .collect(Collectors.toList());
            data.setRecentTransactions(recentTransactionDTOs);
        } else {
            data.setRecentTransactions(Collections.emptyList());
        }
    }

    private void calculateCategoryInventorySummary(DashboardData data, List<Product> allProducts) {
        // Group by category name and sum the QUANTITY
        Map<String, Long> quantityByCategory = allProducts.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,           // Group by category name
                        Collectors.summingLong(Product::getQuantity) // <-- Sum the quantity (long)
                ));

        // Map the results to the updated DTO
        List<CategoryInventoryDTO> categorySummary = quantityByCategory.entrySet().stream()
                // Now passing String and Long to the constructor CategoryInventoryDTO(String, long)
                .map(entry -> new CategoryInventoryDTO(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(CategoryInventoryDTO::getCategoryName)) // Optional: sort by name
                .collect(Collectors.toList());

        data.setCategoryInventorySummary(categorySummary);
        log.debug("Category Inventory Summary (by Quantity): {}", categorySummary);
    }

    // --- Ensure this is the DB Query version ---
    private void calculateMonthlyTransactionSummary(DashboardData data) {
        LocalDateTime startDate = LocalDateTime.now().minusMonths(MONTHS_FOR_SUMMARY).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        log.info("Calculating monthly summary via DB Query from date: {}", startDate);

        List<MonthlyTransactionQueryResult> results = transactionRepository.findMonthlyTransactionSummary(startDate);
        log.debug("Raw monthly results count from DB: {}", results.size());

        Map<YearMonth, MonthlyTransactionSummaryDTO> summaryMap = new TreeMap<>(); // Use TreeMap for sorting

        // Pre-populate the map with target months
        YearMonth currentMonth = YearMonth.now();
        for (int i = 0; i < MONTHS_FOR_SUMMARY; i++) {
            YearMonth ym = currentMonth.minusMonths(i);
            summaryMap.put(ym, new MonthlyTransactionSummaryDTO(
                    ym.getYear(), ym.getMonthValue(), ym.format(DateTimeFormatter.ofPattern("yyyy-MM")), 0L, 0L
            ));
        }

        // Process the query results
        for (MonthlyTransactionQueryResult result : results) {
            if (result.getYear() == null || result.getMonth() == null || result.getTransactionType() == null) {
                log.warn("Skipping monthly result with null values: {}", result);
                continue;
            }
            YearMonth ym = YearMonth.of(result.getYear(), result.getMonth());
            MonthlyTransactionSummaryDTO monthDTO = summaryMap.get(ym);

            if (monthDTO != null) {
                long quantity = result.getTotalQuantity() != null ? result.getTotalQuantity() : 0L; // Handle potential null sum
                if (result.getTransactionType() == Transaction.TransactionType.IN) {
                    monthDTO.setTotalInQuantity(monthDTO.getTotalInQuantity() + quantity);
                } else if (result.getTransactionType() == Transaction.TransactionType.OUT) {
                    monthDTO.setTotalOutQuantity(monthDTO.getTotalOutQuantity() + quantity);
                }
            } else {
                log.warn("Monthly result found for month {} which was not pre-populated in the map.", ym);
            }
        }

        data.setMonthlyTransactionSummary(new ArrayList<>(summaryMap.values()));
        log.debug("Monthly Transaction Summary (DB Query): {}", data.getMonthlyTransactionSummary());
    }


    // --- Mapping functions (as before) ---
    private LowStockProductDTO mapToLowStockProductDTO(Product product) {
        if (product == null) return null;
        return new LowStockProductDTO(product.getId(), product.getName(), product.getSku(), product.getQuantity(), product.getReorder());
    }

    private RecentTransactionDTO mapToRecentTransactionDTO(Transaction transaction) {
        // ... (implementation as before) ...
        if (transaction == null) return null;
        RecentTransactionDTO dto = new RecentTransactionDTO();
        dto.setId(transaction.getId());
        dto.setQuantity(transaction.getQuantity());
        dto.setTransactionType(transaction.getTransactionType().name());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setReference(transaction.getReference());
        if (transaction.getProduct() != null) {
            dto.setProductName(transaction.getProduct().getName());
            dto.setProductSku(transaction.getProduct().getSku());
        } else {
            dto.setProductName("N/A");
            dto.setProductSku("N/A");
        }
        return dto;
    }

    // REMOVE the Java Stream version if it exists:
    // private void calculateMonthlyTransactionSummaryJavaStreams(DashboardData data) { ... }

}