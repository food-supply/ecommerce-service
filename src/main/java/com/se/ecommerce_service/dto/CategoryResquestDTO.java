package com.se.ecommerce_service.dto;

import java.util.UUID;

import com.se.ecommerce_service.validation.Delete;
import com.se.ecommerce_service.validation.Update;

import jakarta.validation.constraints.NotNull;

public class CategoryResquestDTO {
    @NotNull(groups = {Update.class, Delete.class}, message = "Category id not null.")
    private UUID categoryId;
    private String categoryName;

    public CategoryResquestDTO() {
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
