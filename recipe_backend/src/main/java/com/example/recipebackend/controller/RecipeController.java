package com.example.recipebackend.controller;

import com.example.recipebackend.dto.RecipeDtos;
import com.example.recipebackend.mapper.RecipeMapper;
import com.example.recipebackend.model.Recipe;
import com.example.recipebackend.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller for recipe CRUD and listing.
 */
@RestController
@RequestMapping("/api/recipes")
@Tag(name = "Recipes")
public class RecipeController {

    private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService) { this.recipeService = recipeService; }

    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(summary = "List recipes", description = "Paginated list with optional search and category filter")
    public Map<String, Object> list(
            @Parameter(description = "Search query in title/description") @RequestParam(required = false) String q,
            @Parameter(description = "Category id filter") @RequestParam(required = false) Long categoryId,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort spec (e.g., createdAt,desc)") @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        Sort sortObj;
        if (sort.contains(",")) {
            String[] parts = sort.split(",", 2);
            sortObj = Sort.by(Sort.Direction.fromString(parts[1]), parts[0]);
        } else {
            sortObj = Sort.by(Sort.Direction.DESC, sort);
        }
        Pageable pageable = PageRequest.of(page, size, sortObj);
        Page<Recipe> pageData = recipeService.list(q, categoryId, pageable);
        return Map.of(
                "content", pageData.getContent().stream().map(RecipeMapper::toResponse).collect(Collectors.toList()),
                "page", pageData.getNumber(),
                "size", pageData.getSize(),
                "totalElements", pageData.getTotalElements(),
                "totalPages", pageData.getTotalPages()
        );
    }

    // PUBLIC_INTERFACE
    @GetMapping("/{id}")
    @Operation(summary = "Get recipe by id", description = "Returns a single recipe")
    public ResponseEntity<RecipeDtos.RecipeResponse> get(@PathVariable Long id) {
        return recipeService.get(id)
                .map(r -> ResponseEntity.ok(RecipeMapper.toResponse(r)))
                .orElse(ResponseEntity.notFound().build());
    }

    // PUBLIC_INTERFACE
    @PostMapping
    @Operation(summary = "Create recipe", description = "Creates a new recipe (auth optional placeholder)")
    public ResponseEntity<RecipeDtos.RecipeResponse> create(@Valid @RequestBody RecipeDtos.RecipeRequest req) {
        Recipe created = recipeService.create(req);
        return ResponseEntity.ok(RecipeMapper.toResponse(created));
    }

    // PUBLIC_INTERFACE
    @PutMapping("/{id}")
    @Operation(summary = "Update recipe", description = "Updates an existing recipe")
    public ResponseEntity<RecipeDtos.RecipeResponse> update(@PathVariable Long id,
                                                            @Valid @RequestBody RecipeDtos.RecipeRequest req) {
        Recipe updated = recipeService.update(id, req);
        return ResponseEntity.ok(RecipeMapper.toResponse(updated));
    }

    // PUBLIC_INTERFACE
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete recipe", description = "Deletes a recipe by id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
