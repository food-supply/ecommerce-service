package com.se.ecommerce_service.dto;

import java.util.UUID;

import com.se.ecommerce_service.validation.Delete;
import com.se.ecommerce_service.validation.Update;

import jakarta.validation.constraints.NotNull;

public class TagsRequestDTO {
    @NotNull(groups = {Update.class, Delete.class}, message = "Tag id is not null")
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
