package com.se.ecommerce_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.ecommerce_service.dto.WarehouseRequestDTO;
import com.se.ecommerce_service.model.Warehouse;
import com.se.ecommerce_service.response.BaseResponse;
import com.se.ecommerce_service.response.Message;
import com.se.ecommerce_service.service.WarehouseService;
import com.se.ecommerce_service.validation.Delete;
import com.se.ecommerce_service.validation.Update;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/warehouses")
public class WarehouseController {
    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }
    
    @GetMapping("/find-all")
    public ResponseEntity<BaseResponse<List<Warehouse>>> findAll() {
        BaseResponse<List<Warehouse>> response = new BaseResponse<>();
        response.setData(warehouseService.findAll());
        return ResponseEntity.ok().body(response);
    }
    
    @PostMapping("/add")
    public ResponseEntity<BaseResponse<?>> insert(@RequestBody WarehouseRequestDTO dto) {
        boolean ok = warehouseService.insert(dto);
        
        BaseResponse<?> response = new BaseResponse<>();
        response.setErrorCode(ok ? 0 : 1);
        response.setMessage(ok ? Message.SUCCESS : Message.FAIL);
        return ResponseEntity.ok().body(response);
    }
    
    @Validated(Update.class)
    @PostMapping("/update")
    public ResponseEntity<BaseResponse<?>> update(@RequestBody WarehouseRequestDTO dto) {
        boolean ok = warehouseService.update(dto);
        BaseResponse<?> response = new BaseResponse<>();
        response.setErrorCode(ok ? 0 : 1);
        response.setMessage(ok ? Message.SUCCESS : Message.FAIL);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Optional<Warehouse>>> findById(@PathVariable UUID id) {
        BaseResponse<Optional<Warehouse>> response = new BaseResponse<>();
        response.setData(warehouseService.findById(id));
        return ResponseEntity.ok().body(response);
    }
    
    @Validated(Delete.class)
    @PostMapping("/delete")
    public ResponseEntity<BaseResponse<?>> delete(@RequestBody WarehouseRequestDTO dto) {
        boolean ok = warehouseService.delete(dto.getWarehouseId());
        
        BaseResponse<?> response = new BaseResponse<>();
        response.setErrorCode(ok ? 0 : 1);
        response.setMessage(ok ? Message.SUCCESS : Message.FAIL);
        return ResponseEntity.ok().body(response);
    }
    
}
