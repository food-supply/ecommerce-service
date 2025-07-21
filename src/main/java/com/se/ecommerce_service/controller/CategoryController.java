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

import com.se.ecommerce_service.dto.CategoryAttributeRequestDTO;
import com.se.ecommerce_service.dto.CategoryResquestDTO;
import com.se.ecommerce_service.model.Category;
import com.se.ecommerce_service.response.BaseResponse;
import com.se.ecommerce_service.response.Message;
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
    public ResponseEntity <BaseResponse<Optional<Category>>> getCategoryById(@PathVariable UUID id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        BaseResponse<Optional<Category>> response = new BaseResponse<>();
        response.setData(category);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<?>> createCategory(@RequestBody CategoryResquestDTO categoryResquestDTO) {
        boolean ok = categoryService.insertCategory(categoryResquestDTO);
        BaseResponse<?> response = new BaseResponse<>();
        response.setErrorCode(ok ? 0: 1);
        response.setMessage(ok ? Message.SUCCESS : Message.FAIL);
        return  ResponseEntity.ok().body(response);
    }
    
    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<Category>>> getAll() {
        List<Category> categories = categoryService.getAllCategory();
        BaseResponse<List<Category>> response = new BaseResponse<>();
        response.setData(categories);
        return ResponseEntity.ok().body(response);
    }

    @Validated(Delete.class)
    @PostMapping("/delete")
    public ResponseEntity<BaseResponse<?>> delete(@RequestBody CategoryResquestDTO dto) {
        boolean ok = categoryService.deleteCategory(dto.getCategoryId());
        BaseResponse<?> response = new BaseResponse<>();
        response.setErrorCode(ok ? 0 : 1);
        response.setMessage(ok ? Message.SUCCESS : Message.FAIL);
        return ResponseEntity.ok().body(response);
    }
    
    @PostMapping("/insert-category-attribute")
    public ResponseEntity<BaseResponse<?>> insertCategoryAttribute(@RequestBody CategoryAttributeRequestDTO dto) {
        boolean ok = categoryService.insertCategoryAttribute(dto);
        BaseResponse<?> response = new BaseResponse<>();
        response.setErrorCode(ok ? 0 : 1);
        response.setMessage(ok ? Message.SUCCESS : Message.FAIL);
        return ResponseEntity.ok().body(response);
    }
    
}
