package com.example.recipebackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTOs for authentication endpoints.
 */
public class AuthDtos {

    public static class RegisterRequest {
        @Schema(description = "User email", example = "user@example.com")
        @Email @NotBlank
        public String email;

        @Schema(description = "Display name", example = "Jane Doe")
        @NotBlank
        public String displayName;

        @Schema(description = "Password (raw, will be hashed)", example = "P@ssw0rd!")
        @NotBlank @Size(min = 6, max = 100)
        public String password;
    }

    public static class LoginRequest {
        @Schema(description = "User email", example = "user@example.com")
        @Email @NotBlank
        public String email;

        @Schema(description = "Password (raw)", example = "P@ssw0rd!")
        @NotBlank
        public String password;
    }

    public static class AuthResponse {
        @Schema(description = "Simple message or token placeholder")
        public String message;
        @Schema(description = "User ID")
        public Long userId;
        @Schema(description = "User display name")
        public String displayName;

        public AuthResponse(String message, Long userId, String displayName) {
            this.message = message;
            this.userId = userId;
            this.displayName = displayName;
        }
    }
}
