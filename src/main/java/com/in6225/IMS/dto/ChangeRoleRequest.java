package com.in6225.IMS.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeRoleRequest {

    // @NotBlank(message = "New role cannot be blank")
    private String Role;
}
