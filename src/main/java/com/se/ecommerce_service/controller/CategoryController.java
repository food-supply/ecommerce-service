package com.se.ecommerce_service.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.ecommerce_service.dto.CategoryResquestDTO;
import com.se.ecommerce_service.model.Category;
import com.se.ecommerce_service.service.CategoryService;
import com.se.ecommerce_service.validation.Delete;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity <Optional<Category>> getCategoryById(@PathVariable UUID id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return ResponseEntity.ok().body(category);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@RequestBody CategoryResquestDTO categoryResquestDTO) {
        boolean ok = categoryService.insertCategory(categoryResquestDTO);
        return ok ? ResponseEntity.ok().body("Successfully."):
                ResponseEntity.badRequest().body("Fail...");
    }
    
    @GetMapping("/get-all")
    public ResponseEntity<List<Category>> getAll() {
        List<Category> categories = categoryService.getAllCategory();
        return ResponseEntity.ok().body(categories);
    }

    @Validated(Delete.class)
    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody CategoryResquestDTO dto) {
        boolean ok = categoryService.deleteCategory(dto.getCategoryId());
        
        return ok ? ResponseEntity.ok().body("Successfully..."): ResponseEntity.badRequest().body("Fail...");
    }
    
}
