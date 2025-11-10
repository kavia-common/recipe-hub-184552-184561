package com.example.recipebackend.exception;

/**
 * Exception thrown when a requested resource is not found.
 */
public class ResourceNotFoundException extends RuntimeException {
    // PUBLIC_INTERFACE
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
