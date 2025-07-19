package com.se.ecommerce_service.dto;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.se.ecommerce_service.validation.Delete;
import com.se.ecommerce_service.validation.Update;

import jakarta.validation.constraints.NotNull;

public class ProductRequestDTO {
    @NotNull(groups = {Update.class, Delete.class}, message = "Product id not null.")
    private UUID product_id;
    private String productName;
    private String productCode;
    private String brand;
    private UUID categoryId;
    private String description;
    private String status;
    private String unit;

    private List<String> tags = Collections.emptyList();
    private List<ProductVariantRequestDTO> productVariant = Collections.emptyList();
    private List<ProductImage> productImages = Collections.emptyList();

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
   
    public UUID getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

   
    public UUID getProduct_id() {
        return product_id;
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public void setProduct_id(UUID product_id) {
        this.product_id = product_id;
    }
    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public List<ProductVariantRequestDTO> getProductVariant() {
        return productVariant;
    }
    public void setProductVariant(List<ProductVariantRequestDTO> productVariant) {
        this.productVariant = productVariant;
    }
    
    public List<ProductImage> getProductImages() {
        return productImages;
    }
    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }
}
