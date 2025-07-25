package com.se.ecommerce_service.model;

import java.sql.Timestamp;
import java.util.UUID;

public class Cart {
    private UUID cartId;
    private UUID userId;
    private String sessionId;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    public UUID getCartId() {
        return cartId;
    }
    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }
    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }


}
