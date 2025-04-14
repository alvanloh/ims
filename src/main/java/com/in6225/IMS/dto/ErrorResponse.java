// src/main/java/com/in6225/IMS/dto/ErrorResponse.java
package com.in6225.IMS.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error; // e.g., "Not Found", "Bad Request", "Conflict"
    private String message; // Specific error message
    private String path; // Request path
    // Optional: For validation errors
    private Map<String, List<String>> validationErrors;

    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}