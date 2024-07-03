package com.victor.task_management_with_security.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for registering a new user")
public record RegisterRequest (
        @Schema(description = "First name of the user", example = "John")
        @NotEmpty(message = "Firstname is needed for registration")
        @NotBlank(message = "Firstname is needed for registration")
        String firstname,

        @Schema(description = "Last name of the user", example = "Doe")
        @NotEmpty(message = "Lastname is needed for registration")
        @NotBlank(message = "Lastname is needed for registration")
        String lastname,

        @Schema(description = "Email of the user", example = "user@example.com")
        @Email(message = "Email is not properly formatted")
        @NotEmpty(message = "Email is needed for registration")
        @NotBlank(message = "Email is needed for registration")
        String email,

        @Schema(description = "Password of the user", example = "password123")
        @Size(min = 8, message = "Password should be 8 characters or more")
        @NotEmpty(message = "Password is needed for registration")
        @NotBlank(message = "Password is needed for registration")
        String password
) {
}
