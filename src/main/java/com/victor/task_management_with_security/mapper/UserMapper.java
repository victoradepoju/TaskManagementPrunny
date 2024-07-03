package com.victor.task_management_with_security.mapper;

import com.victor.task_management_with_security.dto.auth.RegisterRequest;
import com.victor.task_management_with_security.dto.user.UserResponse;
import com.victor.task_management_with_security.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .email(user.getEmail())
                .noOfTasks(user.getTasks().size())
                .build();
    }

    public User createUserFromRegisterRequest(RegisterRequest registerRequest) {
        return User.builder()
                .firstName(registerRequest.firstname())
                .lastName(registerRequest.lastname())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .build();
    }


}
