package com.se.ecommerce_service.model;

import java.util.UUID;

public class Category {
    private UUID categoryId;
    private String categoryName;

    public Category() {
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
