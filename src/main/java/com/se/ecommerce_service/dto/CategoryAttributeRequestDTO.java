package com.se.ecommerce_service.dto;

import java.util.UUID;

public class CategoryAttributeRequestDTO {
    private UUID id;
    private UUID categoryId;
    private UUID attributeId;
    private boolean isRequired;
    private Integer displayOrder;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }
    public UUID getAttributeId() {
        return attributeId;
    }
    public void setAttributeId(UUID attributeId) {
        this.attributeId = attributeId;
    }
    public boolean isRequired() {
        return isRequired;
    }
    public void setRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }
    public Integer getDisplayOrder() {
        return displayOrder;
    }
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
    
}
