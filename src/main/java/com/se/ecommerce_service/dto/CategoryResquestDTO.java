package com.se.ecommerce_service.dto;

import java.util.UUID;

public class CategoryResquestDTO {
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
