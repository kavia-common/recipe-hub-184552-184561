package com.example.recipebackend.controller;

import com.example.recipebackend.dto.CategoryDtos;
import com.example.recipebackend.model.Category;
import com.example.recipebackend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Category listing controller.
 */
@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories")
public class CategoryController {

    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) { this.categoryService = categoryService; }

    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(summary = "List all categories", description = "Returns available recipe categories")
    public List<CategoryDtos.CategoryResponse> list() {
        return categoryService.listAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    private CategoryDtos.CategoryResponse toDto(Category c) {
        CategoryDtos.CategoryResponse out = new CategoryDtos.CategoryResponse();
        out.id = c.getId();
        out.name = c.getName();
        out.description = c.getDescription();
        return out;
    }
}
