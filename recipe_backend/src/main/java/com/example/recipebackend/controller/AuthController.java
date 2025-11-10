package com.example.recipebackend.controller;

import com.example.recipebackend.dto.AuthDtos;
import com.example.recipebackend.model.User;
import com.example.recipebackend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication endpoints for register and login.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }

    // PUBLIC_INTERFACE
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a user with hashed password using BCrypt")
    public ResponseEntity<AuthDtos.AuthResponse> register(@Valid @RequestBody AuthDtos.RegisterRequest req) {
        User u = authService.register(req);
        return ResponseEntity.ok(new AuthDtos.AuthResponse("registered", u.getId(), u.getDisplayName()));
    }

    // PUBLIC_INTERFACE
    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Validates user credentials; returns a simple response (no JWT for now)")
    public ResponseEntity<AuthDtos.AuthResponse> login(@Valid @RequestBody AuthDtos.LoginRequest req) {
        return authService.login(req)
                .map(u -> ResponseEntity.ok(new AuthDtos.AuthResponse("logged_in", u.getId(), u.getDisplayName())))
                .orElseGet(() -> ResponseEntity.status(401)
                        .body(new AuthDtos.AuthResponse("invalid_credentials", null, null)));
    }
}
