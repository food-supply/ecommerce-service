package com.se.ecommerce_service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.se.ecommerce_service.dto.OrderRequestDTO;
import com.se.ecommerce_service.helper.UUIDUtil;
import com.se.ecommerce_service.mapper.GenericRowMapper;
import com.se.ecommerce_service.model.Order;

@Repository
public class OrderRepository {
    private final JdbcTemplate jdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public List<Order> findAll (){
        RowMapper<Order> rowMapper = new GenericRowMapper<>(Order.class);
        return jdbcTemplate.query("select * from order", rowMapper);
    }

    public Optional<Order> findById (UUID id){
        RowMapper<Order> rowMapper = new GenericRowMapper<>(Order.class);
        List<Order> orders = jdbcTemplate.query("select * from order where order_id = ?", ps -> ps.setBytes(1, UUIDUtil.uuidToBytes(id)), rowMapper);
        return orders.stream().findFirst();
    }

    public boolean insert (OrderRequestDTO dto){
        UUID orderId = UUIDUtil.generateUuidV7();
        Object[] params = {
            orderId,
            dto.getOrderCode(),
            UUIDUtil.uuidToBytes(dto.getCustomerId()),
            dto.getOrderDate(),
            dto.getStatus(),
            dto.getNote(),
            dto.getSellerId()
        };
        int row = jdbcTemplate.update("insert into order (order_id, order_code, customer_id, order_date, status, note, seller_id) values (?, ?, ?, ?, ?, ?, ?)", params);
        return row > 0;
    }

    public boolean update (OrderRequestDTO dto){
        Object[] params = {
            dto.getOrderCode(),
            UUIDUtil.uuidToBytes(dto.getCustomerId()),
            dto.getOrderDate(),
            dto.getStatus(),
            dto.getNote(),
            UUIDUtil.uuidToBytes(dto.getSellerId()),
            UUIDUtil.uuidToBytes(dto.getOrderId())
        };
        int row = jdbcTemplate.update("update order set order_code = ?, customer_id = ?, order_date = ?, status =?, note = ?, seller_id = ? where order_id = ?", params);
        return row > 0;
    }

    public boolean delete (UUID id){
        int row = jdbcTemplate.update("delete from order where order_id = ?", new Object[]{UUIDUtil.uuidToBytes(id)});
        return row > 0;
    }

    // public boolean updateTotalPrice (UUID id){
    //     int row = jdbcTemplate.update("select sum (quantity) as total from order_items where order_items_id = ?", new Object[]{UUIDUtil.uuidToBytes(id)});
    //     return row > 0;
    // }
}
