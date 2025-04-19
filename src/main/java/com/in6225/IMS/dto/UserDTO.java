package com.in6225.IMS.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data // Includes @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    // Password is included for input (create/update) but should NOT be populated in responses
    // We will handle this logic in the mapper/service
    private String password;
    private String role;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String createdBy;
    private String updatedBy;
}