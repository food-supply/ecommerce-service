package com.se.ecommerce_service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.se.ecommerce_service.dto.TagsRequestDTO;
import com.se.ecommerce_service.helper.UUIDUtil;
import com.se.ecommerce_service.mapper.GenericRowMapper;
import com.se.ecommerce_service.model.Tags;

@Repository
public class TagsRepository {
    private final JdbcTemplate jdbcTemplate;

    public TagsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean insertTags(String name) {
        UUID id = UUIDUtil.generateUuidV7();
        int row = jdbcTemplate.update("insert into tag (tag_id, tag_name) values (?, ?)",
                UUIDUtil.uuidToBytes(id), name);
        return row > 0;
    }

    public boolean updateTags(TagsRequestDTO dto) {
        int row = jdbcTemplate.update("insert into tag (tag_id, tag_name) values (?, ?)",
                UUIDUtil.uuidToBytes(dto.getTagId()), dto.getTagName());
        return row > 0;
    }

    public List<Tags> findAll() {
        List<Tags> tags = jdbcTemplate.query("select * from tags",
                new GenericRowMapper<>(Tags.class));
        return tags;
    }

    public List<Tags> findTagByName(String name) {
        RowMapper<Tags> rowMapper = new GenericRowMapper<>(Tags.class);
        List<Tags> tag = jdbcTemplate.query("select * from tag where tag_name = ?",
                rowMapper, name);
        return tag;
    }

    public Optional<Tags> findById(UUID id){
        List<Tags> list = jdbcTemplate.query("SELECT * FROM tag WHERE tag_id = ?",
				ps -> ps.setBytes(1, UUIDUtil.uuidToBytes(id)),
				new GenericRowMapper<>(Tags.class));
		return list.stream().findFirst();
    }
}
