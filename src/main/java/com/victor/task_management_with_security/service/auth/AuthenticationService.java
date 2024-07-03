package com.victor.task_management_with_security.service.auth;

import com.victor.task_management_with_security.dto.auth.AuthResponse;
import com.victor.task_management_with_security.dto.auth.LoginRequest;
import com.victor.task_management_with_security.dto.auth.RegisterRequest;
import com.victor.task_management_with_security.entity.Token;
import com.victor.task_management_with_security.entity.User;
import com.victor.task_management_with_security.exception.EmailAlreadyExistsException;
import com.victor.task_management_with_security.mapper.UserMapper;
import com.victor.task_management_with_security.repository.TokenRepository;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthResponse register(RegisterRequest registerRequest) {
        validateEmailNotExist(registerRequest.email());

        User user = userMapper.createUserFromRegisterRequest(registerRequest);

        userRepository.save(user);

        return buildAuthResponse(
                null,
                "Account successfully created. You can now log in " +
                        "with your new credentials"
        );
    }

    public AuthResponse login(LoginRequest loginRequest) {
        String token = authenticate(loginRequest.email(), loginRequest.password());
        return buildAuthResponse(token, null);
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
//            User user = (User) auth.getPrincipal();
            var user = userRepository.findByEmail(email)
                    .orElseThrow(()-> new UsernameNotFoundException("User not found"));
            var jwt = jwtService.generateToken(user);

            revokeAllUserTokens(user);

            Token token = Token.builder()
                    .token(jwt)
                    .user(user)
                    .expired(false)
                    .revoked(false)
                    .build();
            tokenRepository.save(token);
            return jwt;
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    private void revokeAllUserTokens(User user) {
        var validUserToken = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserToken.isEmpty()) {
            return;
        }
        validUserToken.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserToken);
    }

    private AuthResponse buildAuthResponse(String token, String message) {
        return AuthResponse.builder()
                .token(token)
                .message(message)
                .build();
    }
}
