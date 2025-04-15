package com.in6225.IMS.service;

import com.in6225.IMS.dto.MonthlyTransactionDTO;

import java.util.List;

public interface DashboardService {
    Long getTotalProducts();
    Long getTotalAlertsYTD();
    Long getTotalLowStockProducts();
    Long getTotalOutOfStockProducts();
    List<MonthlyTransactionDTO> getMonthlyInOutSums();
}

