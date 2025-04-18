package com.amol.users_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amol.users_service.client.NotificationClient;
import com.amol.users_service.component.JwtUtil;
import com.amol.users_service.dto.*;
import com.amol.users_service.exception.AuthException;
import com.amol.users_service.exception.UserNotFoundException;
import com.amol.users_service.model.Role;
import com.amol.users_service.model.User;
import com.amol.users_service.repository.UserRepository;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NotificationClient notificationClient;

    public AuthResponse authenticate(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtUtil.generateToken(userDetails);
            log.info("User {} logged in successfully", loginRequest.getUsername());
            return new AuthResponse(jwt);
        } catch (Exception e) {
            log.warn("Authentication failed for user: {}", loginRequest.getUsername());
            throw new AuthException("Invalid username or password");
        }
    }

    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {
        // Validate username and email uniqueness
        validateNewUserCredentials(registerRequest);

        // Create and save new user
        User newUser = createNewUser(registerRequest);
        userRepository.save(newUser);

        // Send welcome email asynchronously
        sendWelcomeEmailAsync(registerRequest);

        log.info("User {} registered successfully", registerRequest.getUsername());
        return new RegisterResponse("User registered successfully");
    }

    private void validateNewUserCredentials(RegisterRequest registerRequest) {
        if (userRepository.findByUsernameAndIsActiveTrue(registerRequest.getUsername()).isPresent()) {
            throw new UserNotFoundException("Username is already taken");
        }
        if (userRepository.findByUsernameAndIsActiveTrue(registerRequest.getEmail()).isPresent()) {
            throw new UserNotFoundException("Email is already in use");
        }
    }

    private User createNewUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.getRoles().add(Role.ROLE_USER.name());
        user.isActive();
        return user;
    }

    /**
     * Sends welcome email asynchronously to avoid blocking the registration process.
     */
    private void sendWelcomeEmailAsync(RegisterRequest registerRequest) {
        CompletableFuture.runAsync(() -> {
            EmailRequest emailRequest = new EmailRequest(
                    registerRequest.getEmail(),
                    registerRequest.getUsername()
            );
            notificationClient.sendWelcomeEmail(emailRequest);
        }).exceptionally(ex -> {
            log.error("Failed to send welcome email to {}", registerRequest.getEmail(), ex);
            return null;
        });
    }

}
