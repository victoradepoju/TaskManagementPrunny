package com.victor.task_management_with_security.dto.user;

import lombok.Builder;

@Builder
public record UserResponse (
        Integer id,
        String firstname,
        String lastname,
        String email,
        Integer noOfTasks
) {
}
