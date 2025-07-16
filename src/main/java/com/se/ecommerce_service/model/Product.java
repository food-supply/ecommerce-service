package com.se.ecommerce_service.model;

import java.util.List;
import java.util.UUID;

public class Product {
    private UUID productId;
    private String productName;
    private String productCode;
    private String brand;
    private UUID categoryId;
    private String unit;
    private String status;
    private String description;
    private List<ProductVariant> productVariant;

    public Product() {
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public List<ProductVariant> getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(List<ProductVariant> productVariant) {
        this.productVariant = productVariant;
    } 
}
