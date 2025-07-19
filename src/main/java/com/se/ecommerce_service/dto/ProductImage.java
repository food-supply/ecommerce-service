package com.se.ecommerce_service.dto;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.se.ecommerce_service.validation.Delete;
import com.se.ecommerce_service.validation.Update;

import jakarta.validation.constraints.NotNull;

public class ProductImage {
    @NotNull(groups = {Update.class, Delete.class}, message = "Product image id not null.")
    private UUID imageId;
    @NotNull(groups = Update.class, message = "Product variant id not null.")
    private UUID variantId;
    private String imageUrl;
    private Boolean isPrimary = false;
    private String description;
    @JsonIgnore
    private MultipartFile  file;

    public UUID getImageId() {
        return imageId;
    }
    public void setImageId(UUID imageId) {
        this.imageId = imageId;
    }
    public UUID getVariantId() {
        return variantId;
    }
    public void setVariantId(UUID variantId) {
        this.variantId = variantId;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public Boolean getIsPrimary() {
        return isPrimary;
    }
    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public MultipartFile getFile() {
        return file;
    }
    public void setFile(MultipartFile file) {
        this.file = file;
    }
    
}
