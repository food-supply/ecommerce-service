package com.se.ecommerce_service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.se.ecommerce_service.dto.OrderRequestDTO;
import com.se.ecommerce_service.model.Order;
import com.se.ecommerce_service.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAll (){
        return orderRepository.findAll();
    }

    public Optional<Order> findById (UUID id){
        return orderRepository.findById(id);
    }

    public boolean insert (OrderRequestDTO dto){
        return orderRepository.insert(dto);
    }

    public boolean update (OrderRequestDTO dto){
        return orderRepository.update(dto);
    }

    public boolean delete (UUID id){
        return orderRepository.delete(id);
    }
    
}
