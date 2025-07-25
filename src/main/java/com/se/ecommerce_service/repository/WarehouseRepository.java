package com.se.ecommerce_service.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.se.ecommerce_service.dto.WarehouseRequestDTO;
import com.se.ecommerce_service.helper.UUIDUtil;
import com.se.ecommerce_service.mapper.GenericRowMapper;
import com.se.ecommerce_service.model.Inventory;
import com.se.ecommerce_service.model.Warehouse;

@Repository
public class WarehouseRepository {
    private final JdbcTemplate jdbcTemplate;

    public WarehouseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public boolean insert (WarehouseRequestDTO dto){
        UUID warehouseId = UUIDUtil.generateUuidV7();
        Object[] params = {
            warehouseId,
            dto.getWarehouseCode(),
            UUIDUtil.uuidToBytes(dto.getSellerId()),
            dto.getWarehouseName(),
            dto.getLocation()
        };
        int row = jdbcTemplate.update("insert into warehouse (warehouse_id, warehouse_code, seller_id, warehouse_name, location) values (?, ?, ?, ?, ?)", params);
        return row > 0;
    }

    public Optional<Warehouse> findById (UUID id){
        RowMapper<Warehouse> rowMapper = new GenericRowMapper<>(Warehouse.class);
        List<Warehouse> warehouse = jdbcTemplate.query("select * from warehouse where warehouse_id = ?", ps -> ps.setBytes(1, UUIDUtil.uuidToBytes(id)), rowMapper);
        return warehouse.stream().findFirst();
    }

    public List<Warehouse> findAll (){
        RowMapper<Warehouse> rowMapper = new GenericRowMapper<>(Warehouse.class);
        List<Warehouse> warehouses = jdbcTemplate.query("select * from warehouse", rowMapper);
        return warehouses;
    }

    public boolean delete(UUID id){
        int row = jdbcTemplate.update("delete from warehouse where warehouse_id = ?", new Object[]{UUIDUtil.uuidToBytes(id)});
        return row > 0;
    }

    public boolean update (WarehouseRequestDTO dto){
        Object[] params = {
            dto.getWarehouseCode(),
            dto.getWarehouseName(),
            dto.getLocation(),
            dto.getWarehouseId(),
            UUIDUtil.uuidToBytes(dto.getSellerId())
        };
        int row = jdbcTemplate.update("update warehouse set warehouse_code = ?, warehouse_name = ?, location = ?, seller_id = ? where warehouse_id = ?", params);
        return row > 0;

    }

    public Inventory checkVariantFree (UUID variantId, BigDecimal quantity){
        String sql = """
                select
                    case ? <= available_stock then available_stock
                    else 0 as available_stock, warehouse_id
                from v_inventory
                where variant_id = ?
                order by available_stock
                limit 1
                """;
        Object[] params = {
                quantity,
                variantId
        };
        Inventory productVariants = jdbcTemplate.queryForObject(sql, params, new GenericRowMapper<>(Inventory.class));
        return productVariants;
    }
    
}
