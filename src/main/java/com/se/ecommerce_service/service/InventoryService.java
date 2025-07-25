package com.se.ecommerce_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.se.ecommerce_service.model.Inventory;
import com.se.ecommerce_service.repository.InventoryRepository;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<Inventory> findByVariantId (UUID id){
        return inventoryRepository.findByVariantId(id);
    }
}
