package com.in6225.IMS.service;

import com.in6225.IMS.dto.AlertDTO;
import java.util.List;

public interface AlertService {

    // Method to create a new alert
    AlertDTO createAlert(AlertDTO alertDTO);

    // Method to update an existing alert
    AlertDTO updateAlert(Long alertId, AlertDTO alertDTO);

    // Method to get an alert by its ID
    AlertDTO getAlertById(Long alertId);

    // Method to get all alerts
    List<AlertDTO> getAllAlerts();

    // Method to delete an alert by its ID
    void deleteAlert(Long alertId);
}

