package com.in6225.IMS.mapper;

import com.in6225.IMS.dto.AlertDTO;
import com.in6225.IMS.entity.Alert;
import com.in6225.IMS.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlertMapper {

    // Convert Alert entity to AlertDTO
    public AlertDTO toAlertDTO(Alert alert) {
        if (alert == null) {
            return null;
        }

        AlertDTO alertDTO = new AlertDTO();
        alertDTO.setId(alert.getId());
        alertDTO.setProductId(alert.getProduct().getId());
        alertDTO.setProductName(alert.getProduct().getName());
        alertDTO.setProductSku(alert.getProduct().getSku());
        alertDTO.setResolved(alert.isResolved());
        alertDTO.setCreatedDate(alert.getCreatedDate());
        alertDTO.setUpdatedDate(alert.getUpdatedDate());
        alertDTO.setCreatedBy(alert.getCreatedBy());
        alertDTO.setUpdatedBy(alert.getUpdatedBy());
        return alertDTO;
    }

    // Convert AlertDTO to Alert entity
    public Alert toAlertEntity(AlertDTO alertDTO) {
        if (alertDTO == null) {
            return null;
        }

        Alert alert = new Alert();
        alert.setId(alertDTO.getId());
        // Assuming you have a way to fetch the Product entity by its ID
        Product product = new Product();
        product.setId(alertDTO.getProductId());
        alert.setProduct(product);
        alert.setResolved(alertDTO.isResolved());
        alert.setCreatedDate(alertDTO.getCreatedDate());
        alert.setUpdatedDate(alertDTO.getUpdatedDate());
        alert.setCreatedBy(alertDTO.getCreatedBy());
        alert.setUpdatedBy(alertDTO.getUpdatedBy());
        return alert;
    }

    // Convert a list of Alert entities to a list of AlertDTOs
    public List<AlertDTO> toAlertDTOList(List<Alert> alerts) {
        return alerts.stream()
                .map(this::toAlertDTO)
                .collect(Collectors.toList());
    }
}

