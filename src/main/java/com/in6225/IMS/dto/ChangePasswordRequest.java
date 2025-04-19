package com.in6225.IMS.dto;

// Add validation annotations if desired (e.g., @NotBlank, @Size)
// import javax.validation.constraints.NotBlank;
// import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

    // @NotBlank(message = "Current password cannot be blank")
    private String currentPassword;

    // @NotBlank(message = "New password cannot be blank")
    // @Size(min = 8, message = "New password must be at least 8 characters long")
    private String newPassword;
}