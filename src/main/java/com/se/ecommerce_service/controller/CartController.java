package com.se.ecommerce_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.se.ecommerce_service.dto.AddToCartRequestDTO;
import com.se.ecommerce_service.response.BaseResponse;
import com.se.ecommerce_service.response.Message;
import com.se.ecommerce_service.service.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse<?>> addToCart(@RequestBody AddToCartRequestDTO req) {
        boolean ok = cartService.addToCart(req);
        BaseResponse<?> response = new BaseResponse<>();
        response.setMessage(ok ? Message.SUCCESS : Message.FAIL);
        response.setErrorCode(ok ? 0 : 1);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/merge")
    public ResponseEntity<BaseResponse<?>> mergeCart(@RequestParam String sessionId, @RequestParam String userId) {
        boolean ok = cartService.mergeCartAfterLogin(sessionId, userId);
        BaseResponse<?> response = new BaseResponse<>();
        response.setErrorCode(ok ? 0 : 1);
        response.setMessage(ok ? Message.SUCCESS : Message.FAIL);
        return ResponseEntity.ok(response);
    }
}