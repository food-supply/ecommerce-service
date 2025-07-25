package com.se.ecommerce_service.repository;

import java.sql.SQLType;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.se.ecommerce_service.helper.UUIDUtil;


@Repository
public class InvoiceRepository {
    private final JdbcTemplate jdbcTemplate;
    
    public InvoiceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<?> getInvoiceItems (UUID order_id){
        UUID seller_id = jdbcTemplate.queryForObject("select seller_id from order where customer_id = ?", new Object[]{UUIDUtil.uuidToBytes(order_id)}, UUID.class);
        String sql = """
                select pv.product_variant_name, o.discount_percent as discount_order, ot.quantity, ot.price from product_variant pv inner join order_items ot on pv.variant_id = ot.variant_id inner join order o on o.order_id = ot.order_id where order_id = ? 
                """;
        return List.of("");
    }
}
