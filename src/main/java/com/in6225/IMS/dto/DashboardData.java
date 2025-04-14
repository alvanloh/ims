// src/main/java/com/in6225/IMS/dto/DashboardData.java
package com.in6225.IMS.dto;

import com.in6225.IMS.dto.CategoryInventoryDTO; // Import new DTO
import com.in6225.IMS.dto.LowStockProductDTO;
import com.in6225.IMS.dto.MonthlyTransactionSummaryDTO; // Import new DTO
import com.in6225.IMS.dto.RecentTransactionDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class DashboardData {
    private long totalProductCount;
    private long totalInventoryQuantity;
    private double totalInventoryValue;
    private long lowStockProductCount;

    private List<LowStockProductDTO> lowStockProducts;
    private List<RecentTransactionDTO> recentTransactions;

    private List<CategoryInventoryDTO> categoryInventorySummary;
    private List<MonthlyTransactionSummaryDTO> monthlyTransactionSummary;
}