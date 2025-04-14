package com.in6225.IMS.controller;

import com.in6225.IMS.dto.AlertDTO;
import com.in6225.IMS.service.AlertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    // Endpoint to create a new alert
    @PostMapping
    public ResponseEntity<AlertDTO> createAlert(@RequestBody AlertDTO alertDTO) {
        AlertDTO createdAlert = alertService.createAlert(alertDTO);
        return new ResponseEntity<>(createdAlert, HttpStatus.CREATED);
    }

    // Endpoint to update an existing alert
    @PutMapping("/{alertId}")
    public ResponseEntity<AlertDTO> updateAlert(@PathVariable Long alertId, @RequestBody AlertDTO alertDTO) {
        AlertDTO updatedAlert = alertService.updateAlert(alertId, alertDTO);
        return ResponseEntity.ok(updatedAlert);
    }

    // Endpoint to get an alert by its ID
    @GetMapping("/{alertId}")
    public ResponseEntity<AlertDTO> getAlertById(@PathVariable Long alertId) {
        AlertDTO alertDTO = alertService.getAlertById(alertId);
        return ResponseEntity.ok(alertDTO);
    }

    // Endpoint to get all alerts
    @GetMapping
    public ResponseEntity<List<AlertDTO>> getAllAlerts() {
        List<AlertDTO> alerts = alertService.getAllAlerts();
        return ResponseEntity.ok(alerts);
    }

    // Endpoint to delete an alert by its ID
    @DeleteMapping("/{alertId}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Long alertId) {
        alertService.deleteAlert(alertId);
        return ResponseEntity.noContent().build();
    }
}

