package com.example.recipebackend.repository;

import com.example.recipebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    // PUBLIC_INTERFACE
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
