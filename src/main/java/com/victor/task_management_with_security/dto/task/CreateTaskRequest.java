package com.victor.task_management_with_security.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record CreateTaskRequest(
        @NotEmpty(message = "Title is needed for new task") // from validation package
        @NotBlank(message = "Title is needed for new task")
        String title,

        @Size(min = 3, message = "Description should be 3 characters or more")
        @NotEmpty(message = "Description is needed for new registration")
        @NotBlank(message = "Description is needed for new registration")
        String description,

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
