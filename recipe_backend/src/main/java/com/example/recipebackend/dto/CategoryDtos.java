package com.example.recipebackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTOs for category endpoints.
 */
public class CategoryDtos {

    public static class CategoryResponse {
        @Schema(description = "Category identifier")
        public Long id;
        @Schema(description = "Category name")
        public String name;
        @Schema(description = "Description")
        public String description;
    }
}
