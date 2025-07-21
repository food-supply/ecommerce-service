package com.se.ecommerce_service.model;

import java.util.UUID;

public class Seller {
    private UUID sellerId;
    private UUID userId;
    private String storeName;
    private String logoURL;
    private String phone;
    private String email;
    private String status;
    
    public UUID getSellerId() {
        return sellerId;
    }
    public void setSellerId(UUID sellerId) {
        this.sellerId = sellerId;
    }
    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public String getLogoURL() {
        return logoURL;
    }
    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
