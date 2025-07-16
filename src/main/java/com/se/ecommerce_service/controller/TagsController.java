package com.se.ecommerce_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.ecommerce_service.model.Tags;
import com.se.ecommerce_service.service.TagsService;

@RestController
@RequestMapping("/tags")
public class TagsController {
    private final TagsService tagsService;

    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }
    
    @PostMapping("/add-tag")
    public ResponseEntity<String> insertTags(@RequestBody String name) {
        boolean ok = tagsService.save(name);
        return ok ? ResponseEntity.ok().body("Successfully."):
            ResponseEntity.badRequest().body("Fail...."); 
    }
    
    @GetMapping("/find-all-tags")
    public ResponseEntity<List<Tags>> findAllTags() {
        return ResponseEntity.ok().body(tagsService.findAll());
    }
}
