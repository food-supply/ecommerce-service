package com.se.ecommerce_service.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

public class ProductBatch {
    private UUID batchId;
    private UUID variantId;
    private UUID warehouseID;
    private String batchNumber;
    private Date importDate;
    private BigDecimal purchasePrice;
    private BigDecimal quantity;
    private String locationInWarehouse;
    
    public UUID getBatchId() {
        return batchId;
    }
    public void setBatchId(UUID batchId) {
        this.batchId = batchId;
    }
    public UUID getVariantId() {
        return variantId;
    }
    public void setVariantId(UUID variantId) {
        this.variantId = variantId;
    }
    public UUID getWarehouseID() {
        return warehouseID;
    }
    public void setWarehouseID(UUID warehouseID) {
        this.warehouseID = warehouseID;
    }
    public String getBatchNumber() {
        return batchNumber;
    }
    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }
    public Date getImportDate() {
        return importDate;
    }
    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }
    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    public BigDecimal getQuantity() {
        return quantity;
    }
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
    public String getLocationInWarehouse() {
        return locationInWarehouse;
    }
    public void setLocationInWarehouse(String locationInWarehouse) {
        this.locationInWarehouse = locationInWarehouse;
    }
}
