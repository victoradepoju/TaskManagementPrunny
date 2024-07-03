package com.victor.task_management_with_security.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Schema(description = "Request body for creating a new task")
public record CreateTaskRequest(
        @Schema(description = "Title of the task", example = "Complete project documentation")
        @NotEmpty(message = "Title is needed for new task")
        @NotBlank(message = "Title is needed for new task")
        String title,

        @Schema(description = "Description of the task", example = "Detailed description of the project documentation")
        @Size(min = 3, message = "Description should be 3 characters or more")
        @NotEmpty(message = "Description is needed for new registration")
        @NotBlank(message = "Description is needed for new registration")
        String description,

        @Schema(description = "Due date of the task in yyyy-MM-dd format", example = "2023-12-31")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        String dueDate
) {
        public CreateTaskRequest {
                if (dueDate == null) {
                        // Default due date is 7 days from creation
                        LocalDate defaultDueDate = LocalDate.now().plusDays(7);
                        dueDate = defaultDueDate.format(DateTimeFormatter.ISO_DATE);
                }
        }
}
