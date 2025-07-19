package com.se.ecommerce_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.ecommerce_service.dto.AttributeRequestDTO;
import com.se.ecommerce_service.model.Attribute;
import com.se.ecommerce_service.service.AttributeService;
import com.se.ecommerce_service.validation.Delete;
import com.se.ecommerce_service.validation.Update;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/attributes")
public class AttributeController {
    private final AttributeService attributeService;

    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Attribute>> getAll() {
        return ResponseEntity.ok().body(attributeService.getAll());
    }

    @Validated(Update.class)
    @PostMapping("/add")
    public ResponseEntity<String> createAttribute(@RequestBody AttributeRequestDTO attributeRequestDTO) {
        boolean ok = attributeService.update(attributeRequestDTO);
        return ok ? ResponseEntity.ok().body("Successfully..") : ResponseEntity.badRequest().body("Fail....");
    }

    @Validated(Delete.class)
    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody AttributeRequestDTO dto) {
        boolean ok = attributeService.delete(dto.getAttributeId());
        return ok ? ResponseEntity.ok().body("Successfully..."): ResponseEntity.badRequest().body("Fail....");
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Optional<Attribute>> getMethodName(@PathVariable UUID id) {
        return ResponseEntity.ok().body(attributeService.findById(id));
    }

}
