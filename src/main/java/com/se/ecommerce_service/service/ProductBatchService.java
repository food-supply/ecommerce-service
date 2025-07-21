package com.se.ecommerce_service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.se.ecommerce_service.dto.ProductBatchRequestDTO;
import com.se.ecommerce_service.model.ProductBatch;
import com.se.ecommerce_service.repository.ProductBatchRepository;

@Service
public class ProductBatchService {
    private final ProductBatchRepository repo;

    public ProductBatchService(ProductBatchRepository repo) {
        this.repo = repo;
    }
    
    public boolean insert (ProductBatchRequestDTO dto){
        return repo.insert(dto);
    }

    public boolean update (ProductBatchRequestDTO dto){
        return repo.update(dto);
    }

    public List<ProductBatch> findAll (){
        return repo.findAll();
    }

    public Optional<ProductBatch> findById (UUID id){
        return repo.findById(id);
    }

    
}
