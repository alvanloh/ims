package com.in6225.IMS.service;

import com.in6225.IMS.dto.DashboardData;

/**
 * Service interface for retrieving data required for the IMS Dashboard.
 */
public interface DashboardService {

    /**
     * Retrieves aggregated data for the dashboard display.
     *
     * @param recentTransactionLimit The maximum number of recent transactions to fetch.
     * @return A DashboardData object containing various metrics.
     */
    DashboardData getDashboardData(int recentTransactionLimit);
}