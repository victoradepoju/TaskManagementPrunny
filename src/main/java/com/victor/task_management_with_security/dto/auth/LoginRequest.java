package com.victor.task_management_with_security.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for logging in")
public record LoginRequest (
        @Schema(description = "Email of the user", example = "user@example.com")
        @NotEmpty(message = "Username is needed for login")
        @NotBlank(message = "Username is needed for login")
        String email,

        @Schema(description = "Password of the user", example = "password123")
        @Size(min = 8, message = "Password should be 8 characters or more")
        @NotEmpty(message = "Password is needed for login")
        @NotBlank(message = "Password is needed for login")
        String password
) {
}
