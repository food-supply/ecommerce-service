package com.se.ecommerce_service.dto;

import java.util.UUID;

import com.se.ecommerce_service.validation.Delete;
import com.se.ecommerce_service.validation.Update;

import jakarta.validation.constraints.NotNull;

public class WarehouseRequestDTO {
    @NotNull(groups = {Update.class, Delete.class}, message = "Warehouse id is not null")
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
