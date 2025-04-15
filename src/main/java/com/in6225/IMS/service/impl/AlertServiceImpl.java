
package com.in6225.IMS.service.impl;

import com.in6225.IMS.dto.AlertDTO;
import com.in6225.IMS.entity.Alert;
import com.in6225.IMS.entity.Product;
import com.in6225.IMS.mapper.AlertMapper;
import com.in6225.IMS.repository.AlertRepository;
import com.in6225.IMS.repository.ProductRepository;
import com.in6225.IMS.service.AlertService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;
    private final ProductRepository productRepository;

    private final AlertMapper alertMapper;

    public AlertServiceImpl(AlertRepository alertRepository,ProductRepository productRepository, AlertMapper alertMapper) {
        this.alertRepository = alertRepository;
        this.productRepository = productRepository;
        this.alertMapper = alertMapper;
    }

    @Override
    public AlertDTO createAlert(AlertDTO alertDTO) {
        // Retrieve the product
        Product product = productRepository.findById(alertDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Alert alert = alertMapper.toAlertEntity(alertDTO);
        alert = alertRepository.save(alert);
        return alertMapper.toAlertDTO(alert);
    }

    @Override
    public AlertDTO updateAlert(Long alertId, AlertDTO alertDTO) {
        Alert existingAlert = alertRepository.findById(alertId).orElseThrow(() -> new RuntimeException("Alert not found"));
        existingAlert.setResolved(alertDTO.isResolved());
        existingAlert = alertRepository.save(existingAlert);
        return alertMapper.toAlertDTO(existingAlert);
    }

    @Override
    public AlertDTO getAlertById(Long alertId) {
        Alert alert = alertRepository.findById(alertId).orElseThrow(() -> new RuntimeException("Alert not found"));
        return alertMapper.toAlertDTO(alert);
    }

    @Override
    public List<AlertDTO> getAllAlerts() {
        List<Alert> alerts = alertRepository.findAllByOrderByIdDesc();
        return alertMapper.toAlertDTOList(alerts);
    }

    @Override
    public void deleteAlert(Long alertId) {
        Alert alert = alertRepository.findById(alertId).orElseThrow(() -> new RuntimeException("Alert not found"));
        alertRepository.delete(alert);
    }
}
