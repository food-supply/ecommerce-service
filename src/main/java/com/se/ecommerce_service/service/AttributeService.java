package com.se.ecommerce_service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.se.ecommerce_service.dto.AttributeRequestDTO;
import com.se.ecommerce_service.model.Attribute;
import com.se.ecommerce_service.repository.AttributeRepository;
import com.se.ecommerce_service.validation.Delete;
import com.se.ecommerce_service.validation.Update;

@Service
public class AttributeService {
    private final AttributeRepository attributeRepository;

    public AttributeService(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }
    
    @Transactional
    public boolean save(AttributeRequestDTO dto){
        return attributeRepository.insertAttribute(dto);
    }

    public List<Attribute> getAll(){
        return attributeRepository.findAll();
    }

    @Transactional
    @Validated(Update.class)
    public boolean update(AttributeRequestDTO attributeRequestDTO){
        return attributeRepository.update(attributeRequestDTO);
    }

    @Validated(Delete.class)
    public boolean delete(UUID id){
        return attributeRepository.delete(id);
    }

    public Optional<Attribute> findById(UUID id){
        return attributeRepository.findById(id);
    }
}
