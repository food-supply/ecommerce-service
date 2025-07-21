package com.se.ecommerce_service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.se.ecommerce_service.dto.WarehouseRequestDTO;
import com.se.ecommerce_service.model.Warehouse;
import com.se.ecommerce_service.repository.WarehouseRepository;
import com.se.ecommerce_service.validation.Delete;
import com.se.ecommerce_service.validation.Update;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public boolean insert(WarehouseRequestDTO dto){
        return warehouseRepository.insert(dto);
    }

    @Validated(Update.class)
    public boolean update (WarehouseRequestDTO dto){
        return warehouseRepository.update(dto);
    }

    @Validated(Delete.class)
    public boolean delete (UUID id){
        return warehouseRepository.delete(id);
    }

    public Optional<Warehouse> findById (UUID id){
        return warehouseRepository.findById(id);
    }

    public List<Warehouse> findAll (){
        return warehouseRepository.findAll();
    }

}
