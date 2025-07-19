package com.se.ecommerce_service.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.ecommerce_service.dto.TagsRequestDTO;
import com.se.ecommerce_service.model.Tags;
import com.se.ecommerce_service.service.TagsService;
import com.se.ecommerce_service.validation.Update;


@RestController
@RequestMapping("/tags")
public class TagsController {
    private final TagsService tagsService;

    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }
    
    @PostMapping("/add")
    public ResponseEntity<String> insertTags(@RequestBody TagsRequestDTO tag) {
        boolean ok = tagsService.save(tag.getTagName());
        return ok ? ResponseEntity.ok().body("Successfully."):
            ResponseEntity.badRequest().body("Fail...."); 
    }
    
    @GetMapping("/find-all")
    public ResponseEntity<List<Tags>> findAllTags() {
        return ResponseEntity.ok().body(tagsService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Tags>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(tagsService.findById(id));
    }

    @Validated(Update.class)
    @PostMapping("/update")
    public ResponseEntity<String> updateTags(@RequestBody  TagsRequestDTO dto) {
        boolean ok = tagsService.update(dto);
        
        return ok ? ResponseEntity.ok().body("Successfully"): ResponseEntity.badRequest().body("Fail...");
    }
    
    
}
