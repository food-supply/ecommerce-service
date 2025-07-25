package com.se.ecommerce_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.ecommerce_service.dto.OrderRequestDTO;
import com.se.ecommerce_service.model.Order;
import com.se.ecommerce_service.response.BaseResponse;
import com.se.ecommerce_service.response.Message;
import com.se.ecommerce_service.service.CartService;
import com.se.ecommerce_service.service.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;

    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping("/find-all")
    public ResponseEntity<BaseResponse<List<Order>>> findAll() {
        List<Order> order = orderService.findAll();
        BaseResponse<List<Order>> response = new BaseResponse<>();
        response.setData(order);
        return ResponseEntity.ok().body(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Optional<Order>>> findById(@PathVariable UUID id) {
        Optional<Order> order = orderService.findById(id);
        BaseResponse<Optional<Order>> response = new BaseResponse<>();
        response.setData(order);
        return ResponseEntity.ok().body(response);
    }
    
    @PostMapping("/add")
    public ResponseEntity<BaseResponse<?>> insert(@RequestBody OrderRequestDTO dto) {
        boolean ok = orderService.insert(dto);
        BaseResponse<?> response = new BaseResponse<>();
        response.setErrorCode(ok ? 0 : 1);
        response.setMessage(ok ? Message.SUCCESS : Message.FAIL);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<BaseResponse<?>> update(@RequestBody OrderRequestDTO dto) {
       boolean ok = orderService.update(dto);
       BaseResponse<?> response = new BaseResponse<>();
       response.setErrorCode(ok ? 0 : 1);
       response.setMessage(ok ? Message.SUCCESS : Message.FAIL);
       return ResponseEntity.ok(response);
    }
    
    @PostMapping("/delete")
    public ResponseEntity<BaseResponse<?>> delete(@RequestBody OrderRequestDTO dto) {
        boolean ok = orderService.delete(dto.getOrderId());
        BaseResponse<?> response = new BaseResponse<>();
        response.setErrorCode(ok ? 0 : 1);
        response.setMessage(ok ? Message.SUCCESS : Message.FAIL);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/valid-order")
    public ResponseEntity<BaseResponse<?>> getValidOrder(@PathVariable UUID usUuid) {
        boolean ok = cartService.mergeValidCartToOrder(usUuid);
        BaseResponse<?> response = new BaseResponse<>();
        response.setErrorCode(ok ? 0 : 1);
        response.setMessage(ok ? Message.SUCCESS : Message.FAIL);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/megre-cart-to-order")
    public ResponseEntity<BaseResponse<?>> getMergeOrder(@PathVariable UUID usUuid) {
        boolean ok = cartService.mergeCartToOrder(usUuid);
        BaseResponse<?> response = new BaseResponse<>();
        response.setErrorCode(ok ? 0 : 1);
        response.setMessage(ok ? Message.SUCCESS : Message.FAIL);
        return ResponseEntity.ok(response);
    }

}
