package com.se.ecommerce_service.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Inventory {
    private UUID variantId;
    private UUID warehouseId;
    private BigDecimal totalIn;
    private BigDecimal totalOut;
    private BigDecimal quantityInStock;
    private BigDecimal reservedStock;
    private BigDecimal availableStock;
    
    public UUID getVariantId() {
        return variantId;
    }
    public void setVariantId(UUID variantId) {
        this.variantId = variantId;
    }
    public UUID getWarehouseId() {
        return warehouseId;
    }
    public void setWarehouseId(UUID warehouseId) {
        this.warehouseId = warehouseId;
    }
    public BigDecimal getTotalIn() {
        return totalIn;
    }
    public void setTotalIn(BigDecimal totalIn) {
        this.totalIn = totalIn;
    }
    public BigDecimal getTotalOut() {
        return totalOut;
    }
    public void setTotalOut(BigDecimal totalOut) {
        this.totalOut = totalOut;
    }
    public BigDecimal getQuantityInStock() {
        return quantityInStock;
    }
    public void setQuantityInStock(BigDecimal quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
    public BigDecimal getReservedStock() {
        return reservedStock;
    }
    public void setReservedStock(BigDecimal reservedStock) {
        this.reservedStock = reservedStock;
    }
    public BigDecimal getAvailableStock() {
        return availableStock;
    }
    public void setAvailableStock(BigDecimal availableStock) {
        this.availableStock = availableStock;
    }

}
