package com.example.recipebackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * DTOs for recipe endpoints.
 */
public class RecipeDtos {

    public static class IngredientDto {
        @Schema(description = "Ingredient name", example = "Flour")
        @NotBlank
        public String name;

        @Schema(description = "Quantity string", example = "2 cups")
        public String quantity;
    }

    public static class RecipeRequest {
        @NotBlank
        @Schema(description = "Recipe title", example = "Pancakes")
        public String title;

        @Schema(description = "Recipe description", example = "Fluffy pancakes with syrup")
        public String description;

        @Schema(description = "Cook time in minutes", example = "15")
        public Integer cookTimeMinutes;

        @Schema(description = "Servings", example = "4")
        public Integer servings;

        @NotNull
        @Schema(description = "Category id")
        public Long categoryId;

        @Schema(description = "Ingredients")
        public List<IngredientDto> ingredients = new ArrayList<>();
    }

    public static class RecipeResponse {
        public Long id;
        public String title;
        public String description;
        public Integer cookTimeMinutes;
        public Integer servings;
        public Long categoryId;
        public String categoryName;
        public List<IngredientDto> ingredients = new ArrayList<>();
    }
}
