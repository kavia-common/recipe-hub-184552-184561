package com.example.recipebackend.config;

import com.example.recipebackend.model.Category;
import com.example.recipebackend.model.Ingredient;
import com.example.recipebackend.model.Recipe;
import com.example.recipebackend.repository.CategoryRepository;
import com.example.recipebackend.repository.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Seeds initial categories and recipes for testing on startup.
 */
@Configuration
public class DataLoader {

    // PUBLIC_INTERFACE
    @Bean
    CommandLineRunner seedData(CategoryRepository categoryRepository,
                               RecipeRepository recipeRepository) {
        return args -> {
            if (categoryRepository.count() > 0) return;

            Category breakfast = categoryRepository.save(new Category("Breakfast", "Start your day right"));
            Category lunch = categoryRepository.save(new Category("Lunch", "Midday meals"));
            Category dinner = categoryRepository.save(new Category("Dinner", "Hearty evening meals"));
            Category dessert = categoryRepository.save(new Category("Dessert", "Sweet treats"));

            Recipe pancakes = new Recipe();
            pancakes.setTitle("Fluffy Pancakes");
            pancakes.setDescription("Classic fluffy pancakes served with maple syrup.");
            pancakes.setCookTimeMinutes(15);
            pancakes.setServings(4);
            pancakes.setCategory(breakfast);
            pancakes.getIngredients().add(new Ingredient("Flour", "2 cups", pancakes));
            pancakes.getIngredients().add(new Ingredient("Milk", "1.5 cups", pancakes));
            pancakes.getIngredients().add(new Ingredient("Eggs", "2", pancakes));

            Recipe salad = new Recipe();
            salad.setTitle("Caesar Salad");
            salad.setDescription("Crisp romaine lettuce with Caesar dressing.");
            salad.setCookTimeMinutes(10);
            salad.setServings(2);
            salad.setCategory(lunch);
            salad.getIngredients().add(new Ingredient("Romaine Lettuce", "1 head", salad));
            salad.getIngredients().add(new Ingredient("Croutons", "1 cup", salad));
            salad.getIngredients().add(new Ingredient("Parmesan", "1/4 cup", salad));

            recipeRepository.save(pancakes);
            recipeRepository.save(salad);
        };
    }
}
