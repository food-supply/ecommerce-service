package com.se.ecommerce_service.model;

import java.util.UUID;

public class Attribute {
    private UUID attributeId;
    private String attributeName;
    private String value;
    
    public Attribute() {
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
