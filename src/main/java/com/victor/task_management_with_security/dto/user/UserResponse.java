package com.victor.task_management_with_security.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Response body containing user details")
public record UserResponse (
        @Schema(description = "ID of the user", example = "1")
        Integer id,

        @Schema(description = "First name of the user", example = "John")
        String firstname,

        @Schema(description = "Last name of the user", example = "Doe")
        String lastname,

        @Schema(description = "Email of the user", example = "john.doe@example.com")
        String email,

        @Schema(description = "Number of tasks assigned to the user", example = "5")
        Integer noOfTasks
) {
}
