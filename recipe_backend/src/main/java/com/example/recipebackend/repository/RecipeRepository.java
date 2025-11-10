package com.example.recipebackend.repository;

import com.example.recipebackend.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Page<Recipe> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String titleQ, String descQ, Pageable pageable
    );

    Page<Recipe> findByCategory_Id(Long categoryId, Pageable pageable);

    Page<Recipe> findByCategory_IdAndTitleContainingIgnoreCaseOrCategory_IdAndDescriptionContainingIgnoreCase(
            Long categoryId1, String titleQ, Long categoryId2, String descQ, Pageable pageable
    );
}
