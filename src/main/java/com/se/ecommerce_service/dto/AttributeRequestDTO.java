package com.se.ecommerce_service.dto;

import java.util.UUID;

import com.se.ecommerce_service.validation.Delete;
import com.se.ecommerce_service.validation.Update;

import jakarta.validation.constraints.NotNull;

public class AttributeRequestDTO {
    @NotNull(groups = {Update.class, Delete.class}, message = "Attribute id not null.")
    private UUID attributeId;
    private String attributeName;
    private String value;
    
    public AttributeRequestDTO() {
    }
    public UUID getAttributeId() {
        return attributeId;
    }
    public void setAttributeId(UUID attributeId) {
        this.attributeId = attributeId;
    }
    public String getAttributeName() {
        return attributeName;
    }
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    
}
