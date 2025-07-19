package com.se.ecommerce_service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.se.ecommerce_service.dto.CategoryResquestDTO;
import com.se.ecommerce_service.model.Category;
import com.se.ecommerce_service.repository.CategoryRepository;
import com.se.ecommerce_service.validation.Delete;
import com.se.ecommerce_service.validation.Update;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    public Optional<Category> getCategoryById (UUID id){
        return categoryRepository.getCategoryById(id);
    }

    public List<Category> getAllCategory (){
        return categoryRepository.getAllCategory();
    }

    public boolean insertCategory (CategoryResquestDTO categoryResquestDTO){
        return categoryRepository.insertCategory(categoryResquestDTO);
    }

    @Validated(Update.class)
    public boolean updateCategory (CategoryResquestDTO categoryResquestDTO){
        return categoryRepository.updateCategory(categoryResquestDTO);
    }

    @Validated(Delete.class)
    public boolean deleteCategory (UUID id){
        return categoryRepository.deleteCategory(id);
    }
}
