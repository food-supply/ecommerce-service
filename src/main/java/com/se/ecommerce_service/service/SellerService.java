package com.se.ecommerce_service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.se.ecommerce_service.dto.SellerRequestDTO;
import com.se.ecommerce_service.model.Seller;
import com.se.ecommerce_service.repository.SellerRepository;

@Service
public class SellerService {
    private final SellerRepository sellerRepository;

    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }
    
    public boolean insert (SellerRequestDTO dto){
        return sellerRepository.insert(dto);
    }

    public boolean update (SellerRequestDTO dto){
        return sellerRepository.update(dto);
    }

    public Optional<Seller> findById (UUID id){
        return sellerRepository.findById(id);
    }

    public List<Seller> findAll (){
        return sellerRepository.findAll();
    }
}
