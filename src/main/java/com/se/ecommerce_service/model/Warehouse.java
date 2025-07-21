package com.se.ecommerce_service.model;

import java.util.UUID;

public class Warehouse {

    private UUID warehouseId;
    private String warehouseCode;
    private String warehouseName;
    private String location;
    private UUID sellerId;

    public UUID getWarehouseId() {
        return warehouseId;
    }
    public void setWarehouseId(UUID warehouseId) {
        this.warehouseId = warehouseId;
    }
    public String getWarehouseCode() {
        return warehouseCode;
    }
    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }
    public String getWarehouseName() {
        return warehouseName;
    }
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public UUID getSellerId() {
        return sellerId;
    }
    public void setSellerId(UUID sellerId) {
        this.sellerId = sellerId;
    }
    
}
