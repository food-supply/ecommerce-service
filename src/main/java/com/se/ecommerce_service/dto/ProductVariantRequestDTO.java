package com.se.ecommerce_service.dto;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.se.ecommerce_service.validation.Delete;
import com.se.ecommerce_service.validation.Update;

import jakarta.validation.constraints.NotNull;

public class ProductVariantRequestDTO {
    @NotNull(groups = {Update.class, Delete.class}, message = "Product variant id not null.")
    private UUID variantId;
    private String skuCode;
    private String productVariantName;
    private BigDecimal retailPrice;
    private BigDecimal baseCost;
    private BigDecimal defaultDiscount;
    private BigDecimal wholeSalePrice;
    private Integer minQty;
    private List<Map<UUID, UUID>> attributes = Collections.emptyList();

    public ProductVariantRequestDTO() {
    }
    public String getSkuCode() {
        return skuCode;
    }
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }
    public BigDecimal getRetailPrice() {
        return retailPrice;
    }
    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }
    public BigDecimal getBaseCost() {
        return baseCost;
    }
    public void setBaseCost(BigDecimal baseCost) {
        this.baseCost = baseCost;
    }
    public BigDecimal getDefaultDiscount() {
        return defaultDiscount;
    }
    public void setDefaultDiscount(BigDecimal defaultDiscount) {
        this.defaultDiscount = defaultDiscount;
    }
    public BigDecimal getWholeSalePrice() {
        return wholeSalePrice;
    }
    public void setWholeSalePrice(BigDecimal wholeSalePrice) {
        this.wholeSalePrice = wholeSalePrice;
    }
    public List<Map<UUID, UUID>> getAttributes() {
        return attributes;
    }
    public void setAttributes(List<Map<UUID, UUID>> attributes) {
        this.attributes = attributes;
    }
    public UUID getVariantId() {
        return variantId;
    }
    public void setVariantId(UUID variantId) {
        this.variantId = variantId;
    }
    public Integer getMinQty() {
        return minQty;
    }
    public void setMinQty(Integer minQty) {
        this.minQty = minQty;
    }
    public String getProductVariantName() {
        return productVariantName;
    }
    public void setProductVariantName(String productVariantName) {
        this.productVariantName = productVariantName;
    }
    
}
