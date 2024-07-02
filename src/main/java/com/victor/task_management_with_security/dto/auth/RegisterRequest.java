package com.victor.task_management_with_security.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegisterRequest (
        @NotEmpty(message = "Firstname is needed for registration")
        @NotBlank(message = "Firstname is needed for registration")
        String firstname,

        @NotEmpty(message = "Lastname is needed for registration")
        @NotBlank(message = "Lastname is needed for registration")
        String lastname,

        @Email(message = "Email is not properly formatted")
        @NotEmpty(message = "Email is needed for registration")
        @NotBlank(message = "Email is needed for registration")
        String email,

        @Size(min = 8, message = "Password should be 8 characters or more")
        @NotEmpty(message = "Password is needed for registration")
        @NotBlank(message = "Password is needed for registration")
        String password
) {
}
