package com.victor.task_management_with_security.dto.auth;

import lombok.Builder;
import lombok.Data;

public record AuthResponse (
    String token
) {}
