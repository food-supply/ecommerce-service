package com.se.ecommerce_service.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.se.ecommerce_service.dto.ProductRequestDTO;
import com.se.ecommerce_service.dto.ProductVariantRequestDTO;
import com.se.ecommerce_service.helper.UUIDUtil;
import com.se.ecommerce_service.mapper.GenericRowMapper;
import com.se.ecommerce_service.model.Product;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM product", new GenericRowMapper<>(Product.class));
    }

    public Optional<Product> findById(UUID id) {
        List<Product> list = jdbcTemplate.query("SELECT * FROM product WHERE product_id = ?",
                ps -> ps.setBytes(1, UUIDUtil.uuidToBytes(id)),
                new GenericRowMapper<>(Product.class));
        return list.stream().findFirst();
    }

    public boolean save(ProductRequestDTO product) {
        UUID uuid = UUIDUtil.generateUuidV7();
        int row = jdbcTemplate.update(
                """
                            INSERT INTO product (product_id, product_code, product_name, category_id, brand, unit, description)
                            VALUES (?, ?, ?, ?, ?, ?, ?)
                        """,
                new Object[] { UUIDUtil.uuidToBytes(uuid) },
                product.getProductCode(), product.getProductName(), new Object[] { UUIDUtil.uuidToBytes(
                        product.getCategoryId()) },
                product.getBrand(),
                product.getUnit(), product.getDescription());
        
        int rowVal = 0;
        int rowAttr = 0;
        for (ProductVariantRequestDTO variant : product.getProductVariant()) {
            UUID variantId = UUIDUtil.generateUuidV7();

            rowAttr = jdbcTemplate.update(
                    """
                                INSERT INTO product_variant (variant_id, product_id, sku_code, base_cost, retail_price, wholesale_price, default_discount)
                                VALUES (?, ?, ?, ?, ?, ?, ?)
                            """,
                    new Object[] { UUIDUtil.uuidToBytes(variantId) }, new Object[] { UUIDUtil.uuidToBytes(uuid) },
                    variant.getSkuCode(),
                    variant.getBaseCost(), variant.getRetailPrice(),
                    variant.getWholeSalePrice(), variant.getDefaultDiscount());

            if (!variant.getAttributes().isEmpty()) {
                for (Map.Entry<UUID, UUID> attr : variant.getAttributes().entrySet()) {
                    UUID id = UUIDUtil.generateUuidV7();
                    rowVal = jdbcTemplate.update("""
                                INSERT INTO product_attribute_value (id, variant_id, attribute_id, value_id)
                                VALUES (?, ?, ?, ?)
                            """, new Object[] { UUIDUtil.uuidToBytes(
                            id) }, new Object[] {
                                    UUIDUtil.uuidToBytes(
                                            variantId) },
                            new Object[] { UUIDUtil.uuidToBytes(
                                    attr.getKey()) },
                            attr.getValue());
                }
            }
        }

        if (!product.getTags().isEmpty())

        {
            int rowTags = jdbcTemplate.update("Insert into product_tag (product_id, tag_id) values (?, ?)",
                    new Object[] { UUIDUtil.uuidToBytes(uuid) }, product.getTags());
            return row > 0 && rowTags > 0 && rowVal > 0 && rowAttr > 0;
        }

        return row > 0 && rowVal > 0 && rowAttr > 0;
    }

    // public boolean update(ProductRequestDTO product) {
    // int row = jdbcTemplate.update(
    // "UPDATE product SET product_name = ?, price = ?, category_id = ?, image_url =
    // ?, is_active = ? WHERE product_id = ?",
    // product.getProductName(), product.getCategoryId(),
    // product.getStatus(), product.getProduct_id());
    // return row > 0;
    // }

    public boolean delete(UUID id) {
        int row = jdbcTemplate.update("DELETE FROM product WHERE product_id = ?",
                new Object[] { UUIDUtil.uuidToBytes(id) });
        return row > 0;
    }

    public List<Product> getProductByCategory(UUID category_id) {
        List<Product> products = jdbcTemplate.query("select * from product where category_id = ?",
                ps -> ps.setBytes(1, UUIDUtil.uuidToBytes(category_id)),
                new GenericRowMapper<>(Product.class));

        return products;
    }

    public List<Product> getProductByIsActive(boolean isActive) {
        List<Product> products = jdbcTemplate.query("select * from product where status = ?",
                new GenericRowMapper<>(Product.class), isActive);
        return products;
    }

    public List<Product> getProductByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        RowMapper<Product> rowMapper = new GenericRowMapper<>(Product.class);
        List<Product> products = jdbcTemplate.query("select * from product where price > ? and price < ?",
                rowMapper, minPrice, maxPrice);
        return products;
    }

    public List<Product> getProductByName(String name) {
        RowMapper<Product> rowMapper = new GenericRowMapper<>(Product.class);
        List<Product> products = jdbcTemplate.query("select * from product where product_name = ?",
                rowMapper, name);
        return products;
    }

    public List<Product> getProductByTag(UUID id) {
        RowMapper<Product> rowMapper = new GenericRowMapper<>(Product.class);
        List<Product> products = jdbcTemplate.query(
                "select p.* from product p inner join product_tag pt on p.product_id = pt.product_tag having pt.tag_id = ? ",
                rowMapper, new Object[]{UUIDUtil.uuidToBytes(id)});
        return products;
    }
}
