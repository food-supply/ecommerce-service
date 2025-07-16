package com.se.ecommerce_service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.se.ecommerce_service.dto.ProductRequestDTO;
import com.se.ecommerce_service.model.Product;
import com.se.ecommerce_service.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Optional<Product> geProductById(UUID id){
        return repo.findById(id);
    }

    public boolean addProduct(ProductRequestDTO product){
        return repo.save(product);
    }

    public boolean deleteProduct (UUID id){
        return repo.delete(id);
    }

    // @Validated(ProductRequestDTO.Update.class)
    // public boolean updateProduct (ProductRequestDTO product){
    //     return repo.update(product);
    // }

    public List<Product> findProductsByName (String name){
        return repo.getProductByName(name);
    }
}
