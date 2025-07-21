package com.se.ecommerce_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.ecommerce_service.dto.AttributeRequestDTO;
import com.se.ecommerce_service.model.Attribute;
import com.se.ecommerce_service.response.BaseResponse;
import com.se.ecommerce_service.response.Message;
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
    public ResponseEntity<BaseResponse<List<Attribute>>> getAll() {
        BaseResponse<List<Attribute>> response = new BaseResponse<>();
        response.setData(attributeService.getAll());
        return ResponseEntity.ok().body(response);
    }

    @Validated(Update.class)
    @PostMapping("/update")
    public ResponseEntity<BaseResponse<?>> updateAttribute(@RequestBody AttributeRequestDTO attributeRequestDTO) {
        boolean ok = attributeService.update(attributeRequestDTO);
        BaseResponse<?> response = new BaseResponse<>();
        response.setErrorCode(ok ? 0 : 1);
        response.setMessage(ok ? Message.SUCCESS : Message.FAIL);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse<?>> createAttribute(@RequestBody AttributeRequestDTO attributeRequestDTO) {
        boolean ok = attributeService.save(attributeRequestDTO);
        BaseResponse<?> response = new BaseResponse<>();
        response.setErrorCode(ok ? 0 : 1);
        response.setMessage(ok ? Message.SUCCESS : Message.FAIL);
        return ResponseEntity.ok().body(response);
    }

    @Validated(Delete.class)
    @PostMapping("/delete")
    public ResponseEntity<BaseResponse<?>> delete(@RequestBody AttributeRequestDTO dto) {
        boolean ok = attributeService.delete(dto.getAttributeId());
        BaseResponse<?> response = new BaseResponse<>();
        response.setErrorCode(ok ? 0 : 1);
        response.setMessage(ok ? Message.SUCCESS : Message.FAIL);
        return ResponseEntity.ok().body(response);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<BaseResponse<Optional<Attribute>>> findById(@PathVariable UUID id) {
        BaseResponse<Optional<Attribute>> response = new BaseResponse<>();
        response.setData(attributeService.findById(id));
        return ResponseEntity.ok().body(response);
    }

}
