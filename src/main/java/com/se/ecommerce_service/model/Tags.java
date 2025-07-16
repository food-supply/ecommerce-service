package com.se.ecommerce_service.model;

import java.util.UUID;

public class Tags {
    private UUID tagId;
    private String tagName;

    public UUID getTagId() {
        return tagId;
    }

    public void setTagId(UUID tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
