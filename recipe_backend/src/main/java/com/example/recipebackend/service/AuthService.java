package com.example.recipebackend.service;

import com.example.recipebackend.dto.AuthDtos;
import com.example.recipebackend.model.User;
import com.example.recipebackend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service layer for authentication logic.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // PUBLIC_INTERFACE
    public User register(AuthDtos.RegisterRequest req) {
        if (userRepository.existsByEmail(req.email)) {
            throw new IllegalArgumentException("Email already in use");
        }
        User u = new User();
        u.setEmail(req.email.toLowerCase());
        u.setDisplayName(req.displayName);
        u.setPasswordHash(passwordEncoder.encode(req.password));
        return userRepository.save(u);
    }

    // PUBLIC_INTERFACE
    public Optional<User> login(AuthDtos.LoginRequest req) {
        return userRepository.findByEmail(req.email.toLowerCase())
                .filter(u -> passwordEncoder.matches(req.password, u.getPasswordHash()));
    }
}
