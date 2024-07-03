package com.victor.task_management_with_security.dto.user;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for editing user profile")
public record ProfileEditRequest(
        @Schema(description = "New first name of the user", example = "John")
        @Size(min = 2, message = "First name should be 2 characters or more")
        String firstName,

        @Schema(description = "New last name of the user", example = "Doe")
        @Size(min = 2, message = "Last name should be 2 characters or more")
        String lastName
) {
}
