package com.amol.users_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.amol.users_service.component.JwtUtil;
import com.amol.users_service.dto.*;
import com.amol.users_service.dto.resetPassword.ForgotPasswordRequest;
import com.amol.users_service.dto.resetPassword.PasswordResetResponse;
import com.amol.users_service.dto.resetPassword.ResetPasswordRequest;
import com.amol.users_service.service.AuthService;
import com.amol.users_service.service.PasswordResetService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final PasswordResetService passwordResetService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {

        AuthResponse response = authService.authenticate(loginRequest);
        log.info("Login successful for user: {}", loginRequest.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/validateToken")
    public ResponseEntity<ValidTokenResponse> validateToken(@RequestParam("token") String token) {
        boolean valid = jwtUtil.validateToken(token);
        ValidTokenResponse response = new ValidTokenResponse(valid);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot")
    public ResponseEntity<PasswordResetResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        PasswordResetResponse response =  passwordResetService.initiatePasswordReset(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset")
    public ResponseEntity<PasswordResetResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        PasswordResetResponse response=  passwordResetService.resetPassword(request);
        return ResponseEntity.ok(response);
    }


}
