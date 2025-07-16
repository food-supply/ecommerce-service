package com.se.ecommerce_service.dto;

import java.util.UUID;

public class AttributeRequestDTO {
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
