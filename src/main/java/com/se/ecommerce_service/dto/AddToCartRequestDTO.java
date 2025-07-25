package com.se.ecommerce_service.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class AddToCartRequestDTO {
    private UUID userId; 
    private String sessionId; 
    private UUID variantId;
    private BigDecimal quantity;

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
    public UUID getVariantId() {
        return variantId;
    }
    public void setVariantId(UUID variantId) {
        this.variantId = variantId;
    }
    public BigDecimal getQuantity() {
        return quantity;
    }
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    
}
