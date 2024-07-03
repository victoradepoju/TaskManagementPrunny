package com.victor.task_management_with_security.dto.user;
import jakarta.validation.constraints.Size;

public record ProfileEditRequest(
        @Size(min = 2, message = "Last name should be 2 characters or more")
        String firstName,

        @Size(min = 2, message = "Last name should be 2 characters or more")
        String lastName
) {
}
