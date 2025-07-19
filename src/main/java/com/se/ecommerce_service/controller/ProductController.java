package com.se.ecommerce_service.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.se.ecommerce_service.dto.ProductRequestDTO;
import com.se.ecommerce_service.model.Product;
import com.se.ecommerce_service.service.ProductService;
import com.se.ecommerce_service.validation.Delete;
import com.se.ecommerce_service.validation.Update;


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

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createProduct (@RequestPart("dto") ProductRequestDTO product,
        @RequestPart("files") List<MultipartFile> files) {

        for (int i = 0; i < product.getProductImages().size(); i++) {
            if (i < files.size()) {
                product.getProductImages().get(i).setFile(files.get(i));
            }
        }

        boolean ok = service.addProduct(product);
        return ok ? ResponseEntity.ok().body("Successfully.") : 
                    ResponseEntity.badRequest().body("Fail...");
    }
    
    @Validated(Update.class)
    @PostMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        boolean ok = service.updateProduct(productRequestDTO);
        return ok ? ResponseEntity.ok().body("Successfully.") :
                    ResponseEntity.badRequest().body("Fail....");
    }

    @Validated(Delete.class)
    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody ProductRequestDTO dto) {
        boolean ok = service.deleteProduct(dto.getProduct_id());
        
        return ok ? ResponseEntity.ok().body("Successfully..."): ResponseEntity.badRequest().body("Fail...");
    }
    
}
