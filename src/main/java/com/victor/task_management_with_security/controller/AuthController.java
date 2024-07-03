package com.victor.task_management_with_security.controller;

import com.victor.task_management_with_security.dto.auth.AuthResponse;
import com.victor.task_management_with_security.dto.auth.LoginRequest;
import com.victor.task_management_with_security.dto.auth.RegisterRequest;
import com.victor.task_management_with_security.service.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(
            summary = "Register a new User",
            description = "An endpoint to register a new User. This " +
                    "user is not authenticated yet and you will have to login with the" +
                    " registered credentials."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 ACCEPTED"
    )
    public ResponseEntity<AuthResponse> register(
            @RequestBody @Valid RegisterRequest registerRequest
    ) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }


    @PostMapping("/login")
    @Operation(
            summary = "User login",
            description = "Login and get a token upon successful login. This token is" +
                    " then used to access restricted endpoints"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<AuthResponse> login(
            @RequestBody @Valid LoginRequest loginRequest
    ) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }
}
