package com.se.ecommerce_service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.se.ecommerce_service.dto.SellerRequestDTO;
import com.se.ecommerce_service.helper.UUIDUtil;
import com.se.ecommerce_service.mapper.GenericRowMapper;
import com.se.ecommerce_service.model.Seller;

@Repository
public class SellerRepository {
    private final JdbcTemplate jdbcTemplate;

    public SellerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public List<Seller> findAll (){
        RowMapper<Seller> rowMapper = new GenericRowMapper<>(Seller.class);
        return jdbcTemplate.query("select * from seller", rowMapper);
    }

    public Optional<Seller> findById (UUID id){
        RowMapper<Seller> rowMapper = new GenericRowMapper<>(Seller.class);
        List<Seller> sellers = jdbcTemplate.query("select * from seller where seller_id = ?", ps -> ps.setBytes(1, UUIDUtil.uuidToBytes(id)), rowMapper);
        return sellers.stream().findFirst();
    }

    public boolean insert (SellerRequestDTO dto){
        UUID sellerId = UUIDUtil.generateUuidV7();
        Object[] params = {
            sellerId,
            UUIDUtil.uuidToBytes(dto.getUserId()),
            dto.getStoreName(),
            dto.getLogoURL(),
            dto.getPhone(), 
            dto.getEmail(),
            dto.getStatus()
        };
        int row = jdbcTemplate.update("insert into seller (seller_id, user_id, store_name, logo_url, phone, email, status) values (?, ?, ?, ?, ?, ?, ?)", params);
        return row > 0;
    }    

    public boolean update (SellerRequestDTO dto){
        Object[] params = {
            UUIDUtil.uuidToBytes(dto.getUserId()),
            dto.getStoreName(),
            dto.getLogoURL(),
            dto.getPhone(),
            dto.getEmail(),
            dto.getStatus()
        };
        int row = jdbcTemplate.update("update seller set user_id = ?, store_name = ?, logo_url = ?, phone = ?, email = ?, status = ? where seller_id = ?", params);
        return row > 0;
    }

    

}
