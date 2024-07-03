package com.victor.task_management_with_security.controller;

import com.victor.task_management_with_security.dto.task.EditTaskRequest;
import com.victor.task_management_with_security.dto.task.TaskResponse;
import com.victor.task_management_with_security.dto.user.ProfileEditRequest;
import com.victor.task_management_with_security.dto.user.UserResponse;
import com.victor.task_management_with_security.service.user.UserService;
import com.victor.task_management_with_security.utils.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<PageResponse<UserResponse>> findAllUsers(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {

        return ResponseEntity.ok(userService.findAllUsers(page, size));
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<UserResponse> findUserById(
            @PathVariable(value = "user-id") Integer userId
    ) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> profile(
            Authentication authenticatedUser
    ) {
        return ResponseEntity.ok(userService.profile(authenticatedUser));
    }

    @PatchMapping("/profile/{user-Id}")
    public ResponseEntity<UserResponse> editUser(
            @PathVariable("user-Id") Integer userId,
            @RequestBody @Valid ProfileEditRequest profileEditRequest,
            Authentication authenticatedUser
    ) {
        return ResponseEntity.ok(userService.edit(
                userId, profileEditRequest, authenticatedUser
        ));
    }

}
