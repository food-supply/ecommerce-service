package com.se.ecommerce_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.se.ecommerce_service.helper.UUIDUtil;
import com.se.ecommerce_service.mapper.GenericRowMapper;
import com.se.ecommerce_service.model.Inventory;

@Repository
public class InventoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public InventoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Inventory> findAll (){
        RowMapper <Inventory> rowMapper = new GenericRowMapper<>(Inventory.class);
        List<Inventory> inventories = jdbcTemplate.query("select * from v_inventory", rowMapper);
        return inventories;
    }

    public List<Inventory> findByVariantId (UUID id){
        RowMapper<Inventory> rowMapper = new GenericRowMapper<>(Inventory.class);
        List<Inventory> inventories = jdbcTemplate.query("select * from v_inventory where variant_id = ?", new Object[]{UUIDUtil.uuidToBytes(id)}, rowMapper);
        return inventories;
    }
}
