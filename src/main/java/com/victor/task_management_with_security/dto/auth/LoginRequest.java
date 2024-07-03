package com.victor.task_management_with_security.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record LoginRequest (
        @NotEmpty(message = "Username is needed for login") // from validation package
        @NotBlank(message = "Username is needed for login")
        String email,

        @Size(min = 8, message = "Password should be 8 characters or more")
        @NotEmpty(message = "Password is needed for login") // from validation package
        @NotBlank(message = "Password is needed for login")
        String password
) {
}
