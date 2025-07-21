package com.se.ecommerce_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.ecommerce_service.dto.ProductBatchRequestDTO;
import com.se.ecommerce_service.model.ProductBatch;
import com.se.ecommerce_service.response.BaseResponse;
import com.se.ecommerce_service.service.ProductBatchService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/product-batch")
public class ProductBatchController {
    private final ProductBatchService productBatchService;

    public ProductBatchController(ProductBatchService productBatchService) {
        this.productBatchService = productBatchService;
    }
    
    @GetMapping("/find-all")
    public ResponseEntity<BaseResponse <List<ProductBatch>>> findAll() {
        BaseResponse<List<ProductBatch>> response = new BaseResponse<>();
        response.setData(productBatchService.findAll());
        response.setErrorCode(0);
        response.setMessage("Success");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestBody ProductBatchRequestDTO dto) {
        boolean ok = productBatchService.insert(dto);
        return ok ? ResponseEntity.ok().body("Successfully..."): ResponseEntity.badRequest().body("Fail...");
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductBatch>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(productBatchService.findById(id));
    }
    
}
