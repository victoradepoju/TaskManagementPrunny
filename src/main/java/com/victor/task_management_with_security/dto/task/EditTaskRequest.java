package com.victor.task_management_with_security.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

@Schema(description = "Request body for editing an existing task")
public record EditTaskRequest (
        @Schema(description = "New title for the task", example = "Update project documentation")
        @Size(min = 3, message = "New title should be 3 characters or more")
        String title,

        @Schema(description = "New description for the task", example = "Updated detailed description of the project documentation")
        @Size(min = 3, message = "New Description should be 3 characters or more")
        String description,

        @Schema(description = "New due date for the task in yyyy-MM-dd format", example = "2024-01-15")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        String dueDate
) {
}
