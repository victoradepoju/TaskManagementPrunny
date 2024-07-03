package com.victor.task_management_with_security.dto.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskResponse(
        Integer id,

        String title,

        String description,

        String owner,

        boolean status,

        LocalDate dueDate,

        LocalDateTime createdAt,

        LocalDateTime modifiedAt
) {
}
