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

import com.se.ecommerce_service.dto.ProductRequestDTO;
import com.se.ecommerce_service.model.Product;
import com.se.ecommerce_service.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Product>> getAllProduct() {
        return ResponseEntity.ok().body(service.getAllProducts());
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> findProductById (@PathVariable UUID id) {
        Optional<Product> product = service.geProductById(id);
        return ResponseEntity.ok().body(product);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createProduct (@RequestBody ProductRequestDTO product) {
        boolean ok = service.addProduct(product);
        return ok ? ResponseEntity.ok().body("Successfully.") : 
                    ResponseEntity.badRequest().body("Fail...");
    }
    
    // @Validated(ProductRequestDTO.Update.class)
    // @PostMapping("/update")
    // public ResponseEntity<String> postMethodName(@RequestBody ProductRequestDTO productRequestDTO) {
    //     boolean ok = service.updateProduct(productRequestDTO);
    //     return ok ? ResponseEntity.ok().body("Successfully.") :
    //                 ResponseEntity.badRequest().body("Fail....");
    // }
}
