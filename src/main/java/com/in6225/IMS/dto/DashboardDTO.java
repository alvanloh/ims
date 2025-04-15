package com.in6225.IMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {
    private Long totalProducts;
    private Long totalAlertsYTD;
    private Long totalLowStockProducts;
    private Long totalOutOfStockProducts;
}
