package com.se.ecommerce_service.dto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProductVariantRequestDTO {
    private String color;
    private String size;
    private String skuCode;
    private BigDecimal retailPrice;
    private BigDecimal baseCost;
    private BigDecimal defaultDiscount;
    private BigDecimal wholeSalePrice;
    private Map<UUID, UUID> attributes = new HashMap<>();

    public ProductVariantRequestDTO() {
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
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
    public Map<UUID, UUID> getAttributes() {
        return attributes;
    }
    public void setAttributes(Map<UUID, UUID> attributes) {
        this.attributes = attributes;
    }
    
}
