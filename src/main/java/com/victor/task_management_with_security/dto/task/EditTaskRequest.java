package com.victor.task_management_with_security.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

public record EditTaskRequest (
        @Size(min = 3, message = "New title should be 3 characters or more")
        String title,

        @Size(min = 3, message = "New Description should be 3 characters or more")
        String description,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        String dueDate
) {
}
