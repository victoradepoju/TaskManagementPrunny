package com.victor.task_management_with_security.mapper;

import com.victor.task_management_with_security.dto.auth.RegisterRequest;
import com.victor.task_management_with_security.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    public User createUserFromRegisterRequest(RegisterRequest registerRequest) {
        return User.builder()
                .firstName(registerRequest.firstname())
                .lastName(registerRequest.lastname())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .build();
    }
}
