package com.se.ecommerce_service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.se.ecommerce_service.dto.ProductBatchRequestDTO;
import com.se.ecommerce_service.helper.UUIDUtil;
import com.se.ecommerce_service.mapper.GenericRowMapper;
import com.se.ecommerce_service.model.ProductBatch;

@Repository
public class ProductBatchRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductBatchRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductBatch> findAll (){
        RowMapper<ProductBatch> rowMapper = new GenericRowMapper<>(ProductBatch.class);
        List<ProductBatch> productPatchs = jdbcTemplate.query("select * from product_batch", rowMapper);
        return productPatchs;
    }

    public Optional<ProductBatch> findById (UUID id){
        RowMapper<ProductBatch> rowMapper = new GenericRowMapper<>(ProductBatch.class);
        List<ProductBatch> productPatchs = jdbcTemplate.query("select * from product_batch where batch_id = ?", ps -> ps.setBytes(1, UUIDUtil.uuidToBytes(id)), rowMapper);
        return productPatchs.stream().findFirst();
    }

    public boolean update (ProductBatchRequestDTO dto){
        Object[] params = {
            UUIDUtil.uuidToBytes(dto.getVariantId()),
            UUIDUtil.uuidToBytes(dto.getWarehouseID()),
            dto.getBatchNumber(),
            dto.getImportDate(),
            dto.getPurchasePrice(),
            dto.getQuantity(),
            dto.getLocationInWarehouse(),
            dto.getBatchId()
        };

        int row = jdbcTemplate.update("update product_batch set variant_id = ?, warehouse_id = ?, batch_number = ?, import_date = ?, purchase_price = ?, quantity = ?, location_in_warehouse = ?  where batch_id = ?", params);

        return row > 0;
    }

    public boolean insert (ProductBatchRequestDTO dto){
        UUID batch_id = UUIDUtil.generateUuidV7();
        Object[] params = {
            batch_id,
            UUIDUtil.uuidToBytes(dto.getVariantId()),
            UUIDUtil.uuidToBytes(dto.getWarehouseID()),
            dto.getBatchNumber(),
            dto.getImportDate(),
            dto.getPurchasePrice(),
            dto.getQuantity(),
            dto.getLocationInWarehouse()
        };

        int row = jdbcTemplate.update("insert into product_batch (batch_id, variant_id, warehouse_id, batch_number, import_date, purchase_price, quantity, location_in_warehouse) values (?, ?, ?, ?, ?, ?, ?, ?)", params);
        return row > 0 ;
    }

    public boolean delete (UUID id){
        int row = jdbcTemplate.update("delete from product_batch where batch_id = ? ", new Object[]{UUIDUtil.uuidToBytes(id)});
        return row > 0;
    }
}
