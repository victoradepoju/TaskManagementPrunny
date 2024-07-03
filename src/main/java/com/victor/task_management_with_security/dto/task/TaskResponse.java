package com.victor.task_management_with_security.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Response body containing task details")
public record TaskResponse(
        @Schema(description = "ID of the task", example = "1")
        Integer id,

        @Schema(description = "Title of the task", example = "Complete project documentation")
        String title,

        @Schema(description = "Description of the task", example = "Detailed description of the project documentation")
        String description,

        @Schema(description = "Owner of the task", example = "John Doe")
        String owner,

        @Schema(description = "Status of the task", example = "false")
        boolean status,

        @Schema(description = "Due date of the task", example = "2023-12-31")
        LocalDate dueDate,

        @Schema(description = "Creation timestamp of the task", example = "2023-07-03T10:15:30")
        LocalDateTime createdAt,

        @Schema(description = "Last modification timestamp of the task", example = "2023-07-04T11:20:35")
        LocalDateTime modifiedAt
) {
}
