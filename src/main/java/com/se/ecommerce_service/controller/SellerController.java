package com.se.ecommerce_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.ecommerce_service.dto.SellerRequestDTO;
import com.se.ecommerce_service.model.Seller;
import com.se.ecommerce_service.response.BaseResponse;
import com.se.ecommerce_service.response.Message;
import com.se.ecommerce_service.service.SellerService;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/sellers")
public class SellerController {
    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }
    
    @GetMapping("{id}")
    public ResponseEntity<BaseResponse<Optional<Seller>>> findById(@PathVariable UUID id) {
        BaseResponse<Optional<Seller>> response = new BaseResponse<>();
        response.setData(sellerService.findById(id));
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse<?>> insert(@RequestBody SellerRequestDTO dto) {
        boolean ok = sellerService.insert(dto);
        BaseResponse<?> response = new BaseResponse<>();
        response.setMessage(ok ? Message.SUCCESS : Message.FAIL);
        response.setErrorCode(ok ? 0 : 1);
        return ResponseEntity.ok().body(response);
    }
    
    
}
