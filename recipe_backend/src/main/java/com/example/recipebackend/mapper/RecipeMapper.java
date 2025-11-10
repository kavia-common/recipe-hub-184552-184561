package com.example.recipebackend.mapper;

import com.example.recipebackend.dto.RecipeDtos;
import com.example.recipebackend.model.Category;
import com.example.recipebackend.model.Ingredient;
import com.example.recipebackend.model.Recipe;

import java.util.stream.Collectors;

/**
 * Mapper utility for Recipe conversions.
 */
public class RecipeMapper {

    // PUBLIC_INTERFACE
    public static Recipe toEntity(RecipeDtos.RecipeRequest dto, Category category) {
        Recipe r = new Recipe();
        r.setTitle(dto.title);
        r.setDescription(dto.description);
        r.setCookTimeMinutes(dto.cookTimeMinutes);
        r.setServings(dto.servings);
        r.setCategory(category);
        r.getIngredients().clear();
        if (dto.ingredients != null) {
            for (RecipeDtos.IngredientDto ing : dto.ingredients) {
                Ingredient entity = new Ingredient();
                entity.setName(ing.name);
                entity.setQuantity(ing.quantity);
                entity.setRecipe(r);
                r.getIngredients().add(entity);
            }
        }
        return r;
    }

    // PUBLIC_INTERFACE
    public static void updateEntity(Recipe r, RecipeDtos.RecipeRequest dto, Category category) {
        r.setTitle(dto.title);
        r.setDescription(dto.description);
        r.setCookTimeMinutes(dto.cookTimeMinutes);
        r.setServings(dto.servings);
        r.setCategory(category);
        // replace ingredients
        r.getIngredients().clear();
        if (dto.ingredients != null) {
            for (RecipeDtos.IngredientDto ing : dto.ingredients) {
                Ingredient entity = new Ingredient();
                entity.setName(ing.name);
                entity.setQuantity(ing.quantity);
                entity.setRecipe(r);
                r.getIngredients().add(entity);
            }
        }
    }

    // PUBLIC_INTERFACE
    public static RecipeDtos.RecipeResponse toResponse(Recipe r) {
        RecipeDtos.RecipeResponse resp = new RecipeDtos.RecipeResponse();
        resp.id = r.getId();
        resp.title = r.getTitle();
        resp.description = r.getDescription();
        resp.cookTimeMinutes = r.getCookTimeMinutes();
        resp.servings = r.getServings();
        if (r.getCategory() != null) {
            resp.categoryId = r.getCategory().getId();
            resp.categoryName = r.getCategory().getName();
        }
        resp.ingredients = r.getIngredients().stream().map(e -> {
            RecipeDtos.IngredientDto d = new RecipeDtos.IngredientDto();
            d.name = e.getName();
            d.quantity = e.getQuantity();
            return d;
        }).collect(Collectors.toList());
        return resp;
    }
}
