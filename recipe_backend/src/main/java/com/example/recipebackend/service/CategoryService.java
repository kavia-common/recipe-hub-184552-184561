package com.example.recipebackend.service;

import com.example.recipebackend.model.Category;
import com.example.recipebackend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for Category operations.
 */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // PUBLIC_INTERFACE
    public List<Category> listAll() {
        return categoryRepository.findAll();
    }

    // PUBLIC_INTERFACE
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found: " + id));
    }
}
