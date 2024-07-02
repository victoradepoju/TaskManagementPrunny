package com.victor.task_management_with_security.service.auth;

import com.victor.task_management_with_security.dto.auth.AuthResponse;
import com.victor.task_management_with_security.dto.auth.LoginRequest;
import com.victor.task_management_with_security.dto.auth.RegisterRequest;
import com.victor.task_management_with_security.entity.User;
import com.victor.task_management_with_security.exception.EmailAlreadyExistsException;
import com.victor.task_management_with_security.mapper.UserMapper;
import com.victor.task_management_with_security.repository.UserRepository;
import com.victor.task_management_with_security.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthResponse register(RegisterRequest registerRequest) {
        validateEmailNotExist(registerRequest.email());

        User user = userMapper.createUserFromRegisterRequest(registerRequest);

        userRepository.save(user);

        String result = authenticate(registerRequest.email(), registerRequest.password());

        return buildAuthResponse(result);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        String token = authenticate(loginRequest.email(), loginRequest.password());
        return buildAuthResponse(token);
    }

    private void validateEmailNotExist(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
    }

    private String authenticate(String email, String password) {
        Authentication authenticationRequest = new UsernamePasswordAuthenticationToken(email, password);

        try {
            Authentication auth = authenticationManager.authenticate(authenticationRequest);
            User user = (User) auth.getPrincipal();
            return jwtService.generateToken(user);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    private AuthResponse buildAuthResponse(String result) {
        return new AuthResponse(result);
    }
}
