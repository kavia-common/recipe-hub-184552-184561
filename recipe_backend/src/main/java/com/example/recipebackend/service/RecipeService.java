package com.example.recipebackend.service;

import com.example.recipebackend.dto.RecipeDtos;
import com.example.recipebackend.exception.ResourceNotFoundException;
import com.example.recipebackend.mapper.RecipeMapper;
import com.example.recipebackend.model.Category;
import com.example.recipebackend.model.Recipe;
import com.example.recipebackend.repository.CategoryRepository;
import com.example.recipebackend.repository.RecipeRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Service for Recipe operations.
 */
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;

    public RecipeService(RecipeRepository recipeRepository,
                         CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
    }

    private Category requireCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryId));
    }

    // PUBLIC_INTERFACE
    public Page<Recipe> list(String q, Long categoryId, Pageable pageable) {
        if (categoryId != null && StringUtils.hasText(q)) {
            String like = q.trim();
            return recipeRepository
                    .findByCategory_IdAndTitleContainingIgnoreCaseOrCategory_IdAndDescriptionContainingIgnoreCase(
                            categoryId, like, categoryId, like, pageable);
        } else if (categoryId != null) {
            return recipeRepository.findByCategory_Id(categoryId, pageable);
        } else if (StringUtils.hasText(q)) {
            String like = q.trim();
            return recipeRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(like, like, pageable);
        } else {
            return recipeRepository.findAll(pageable);
        }
    }

    // PUBLIC_INTERFACE
    public Optional<Recipe> get(Long id) {
        return recipeRepository.findById(id);
    }

    // PUBLIC_INTERFACE
    public Recipe create(RecipeDtos.RecipeRequest req) {
        Category category = requireCategory(req.categoryId);
        Recipe r = RecipeMapper.toEntity(req, category);
        return recipeRepository.save(r);
    }

    // PUBLIC_INTERFACE
    public Recipe update(Long id, RecipeDtos.RecipeRequest req) {
        Recipe r = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found: " + id));
        Category category = requireCategory(req.categoryId);
        RecipeMapper.updateEntity(r, req, category);
        return recipeRepository.save(r);
    }

    // PUBLIC_INTERFACE
    public void delete(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recipe not found: " + id);
        }
        recipeRepository.deleteById(id);
    }
}
