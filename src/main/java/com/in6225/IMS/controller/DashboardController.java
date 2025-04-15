package com.in6225.IMS.controller;

import com.in6225.IMS.dto.DashboardDTO;
import com.in6225.IMS.dto.MonthlyTransactionDTO;
import com.in6225.IMS.dto.ProductDTO;
import com.in6225.IMS.dto.TransactionDTO;
import com.in6225.IMS.service.DashboardService;
import com.in6225.IMS.service.ProductService;
import com.in6225.IMS.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final ProductService productService;
    private final TransactionService transactionService;

    @Autowired
    public DashboardController(DashboardService dashboardService, ProductService productService, TransactionService transactionService) {
        this.dashboardService = dashboardService;
        this.productService = productService;
        this.transactionService = transactionService;
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardDTO> getDashboardStats() {
        DashboardDTO dashboardDTO = new DashboardDTO(
            dashboardService.getTotalProducts(),
            dashboardService.getTotalAlertsYTD(),
            dashboardService.getTotalLowStockProducts(),
            dashboardService.getTotalOutOfStockProducts()
        );
        return ResponseEntity.ok(dashboardDTO);
    }

    @GetMapping("/monthly-transactions")
    public ResponseEntity<List<MonthlyTransactionDTO>> getMonthlyTransactions() {
        List<MonthlyTransactionDTO> monthlySums = dashboardService.getMonthlyInOutSums();
        return ResponseEntity.ok(monthlySums);
    }

    @GetMapping("/low-stocks")
    public ResponseEntity<List<ProductDTO>> getLowStockProducts() {
        List<ProductDTO> products = productService.getLowStockProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/todays-transactions")
    public ResponseEntity<List<TransactionDTO>> getTodaysTransactions () {
        List<TransactionDTO> transactions = transactionService.getTodaysTransactions();
        return ResponseEntity.ok(transactions);
    }
}
