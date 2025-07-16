package com.se.ecommerce_service.service;

import org.springframework.stereotype.Service;

import com.se.ecommerce_service.dto.AttributeRequestDTO;
import com.se.ecommerce_service.repository.AttributeRepository;

@Service
public class AttributeService {
    private final AttributeRepository attributeRepository;

    public AttributeService(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }
    
    public boolean save(AttributeRequestDTO dto){
        return attributeRepository.insertAttribute(dto);
    }
}
