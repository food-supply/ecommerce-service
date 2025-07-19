package com.se.ecommerce_service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.se.ecommerce_service.dto.CategoryResquestDTO;
import com.se.ecommerce_service.helper.UUIDUtil;
import com.se.ecommerce_service.mapper.GenericRowMapper;
import com.se.ecommerce_service.model.Category;

@Repository
public class CategoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Category> getCategoryById(UUID id) {
        List<Category> list = jdbcTemplate.query("select * from category where category_id = ?",
                ps -> ps.setBytes(1, UUIDUtil.uuidToBytes(id)),
                new GenericRowMapper<>(Category.class));
        return list.stream().findFirst();
    }

    public List<Category> getAllCategory() {
        return jdbcTemplate.query("select * from category",
                new GenericRowMapper<>(Category.class));
    }

    public boolean insertCategory(CategoryResquestDTO categoryResquestDTO) {
        UUID uuid = UUIDUtil.generateUuidV7();
        int row = jdbcTemplate.update("insert into category (category_id, category_name) values (?, ?)",
                new Object[] { UUIDUtil.uuidToBytes(uuid), categoryResquestDTO.getCategoryName() });
        return row > 0;
    }

    public boolean updateCategory(CategoryResquestDTO categoryResquestDTO) {
        int row = jdbcTemplate.update("update category set category_name = ? where category_id = ?",
                categoryResquestDTO.getCategoryName(), UUIDUtil.uuidToBytes(categoryResquestDTO.getCategoryId()));

        return row > 0;
    }

    public boolean deleteCategory(UUID id) {
        int row = jdbcTemplate.update("delete from category where category_id = ?",
                new Object[] { UUIDUtil.uuidToBytes(id) });
        return row > 0;
    }
}
