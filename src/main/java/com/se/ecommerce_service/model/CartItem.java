package com.se.ecommerce_service.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

public class CartItem {
    private UUID cartItemId;
    private UUID cartId;
    private UUID variantId;
    private BigDecimal quantity;
    private BigDecimal priceAtAddTime;
    private Timestamp addedAt;
    
    public UUID getCartItemId() {
        return cartItemId;
    }
    public void setCartItemId(UUID cartItemId) {
        this.cartItemId = cartItemId;
    }
    public UUID getCartId() {
        return cartId;
    }
    public void setCartId(UUID cartId) {
        this.cartId = cartId;
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
    public BigDecimal getPriceAtAddTime() {
        return priceAtAddTime;
    }
    public void setPriceAtAddTime(BigDecimal priceAtAddTime) {
        this.priceAtAddTime = priceAtAddTime;
    }
    public Timestamp getAddedAt() {
        return addedAt;
    }
    public void setAddedAt(Timestamp addedAt) {
        this.addedAt = addedAt;
    }

    
}
