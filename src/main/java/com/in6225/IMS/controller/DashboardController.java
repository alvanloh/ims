package com.in6225.IMS.controller;

import com.in6225.IMS.dto.DashboardData;
import com.in6225.IMS.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard") // Base path for dashboard related endpoints
@Tag(name = "Dashboard", description = "API endpoints for retrieving dashboard summary data") // For Swagger/OpenAPI documentation
public class DashboardController {

    private static final Logger log = LoggerFactory.getLogger(DashboardController.class);

    private final DashboardService dashboardService;

    // Constructor injection is preferred
    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * GET /api/v1/dashboard : Retrieves summary data for the dashboard.
     *
     * @param limit Optional query parameter to specify the maximum number of recent transactions to return. Defaults to 10.
     * @return ResponseEntity containing DashboardData and HTTP status OK.
     */
    @GetMapping
    @Operation(summary = "Get Dashboard Summary Data",
            description = "Retrieves key metrics for the inventory management system dashboard, including counts, values, low stock items, and recent transactions.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved dashboard data",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DashboardData.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error occurred")
            })
    public ResponseEntity<DashboardData> getDashboardData(
            @Parameter(description = "Maximum number of recent transactions to fetch. Default is 10.", example = "5")
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {

        log.info("Received request for dashboard data with recent transaction limit: {}", limit);

        // Ensure limit is not negative, although defaultValue handles common cases
        int effectiveLimit = Math.max(0, limit);

        DashboardData dashboardData = dashboardService.getDashboardData(effectiveLimit);

        log.info("Successfully retrieved dashboard data.");
        return ResponseEntity.ok(dashboardData); // Returns 200 OK status with the data in the body
    }

    // You could add more specific dashboard endpoints here later if needed
    // e.g., GET /api/dashboard/low-stock
    // e.g., GET /api/dashboard/transaction-summary
}