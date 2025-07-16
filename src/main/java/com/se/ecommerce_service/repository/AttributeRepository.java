package com.se.ecommerce_service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.se.ecommerce_service.dto.AttributeRequestDTO;
import com.se.ecommerce_service.helper.UUIDUtil;
import com.se.ecommerce_service.mapper.GenericRowMapper;
import com.se.ecommerce_service.model.Attribute;

@Repository
public class AttributeRepository {
    private final JdbcTemplate jdbcTemplate;

    public AttributeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean insertAttribute(AttributeRequestDTO attributeRequestDTO) {
        UUID id = UUIDUtil.generateUuidV7();
        UUID valueId = UUIDUtil.generateUuidV7();
        int row = jdbcTemplate.update("insert into attribute (attribute_id, attribute_name) values (?, ?)",
                new Object[] { UUIDUtil.uuidToBytes(id) }, attributeRequestDTO.getAttributeName());
        int rowVal = jdbcTemplate.update("insert into attribute_value (value_id, attribute_id, value) values (?, ?, ?)",
                new Object[] { UUIDUtil.uuidToBytes(valueId) }, new Object[] { UUIDUtil.uuidToBytes(id) },
                attributeRequestDTO.getValue());
        return row > 0 && rowVal > 0;
    }

    public Optional<Attribute> findById(UUID id) {
        RowMapper<Attribute> rowMapper = new GenericRowMapper<>(Attribute.class);
        List<Attribute> attr = jdbcTemplate.query(
                "select attr.attribute_id. attr.attribute_name, attr_val.value from attribute attr inner join attribute_value attr_val on attr.attribute_id = attr_val.attribute_id having attr.attribute_id = ? ",
                ps -> ps.setBytes(1, UUIDUtil.uuidToBytes(id)), rowMapper);
        return attr.stream().findFirst();
    }

    public List<Attribute> findAll() {
        RowMapper<Attribute> rowMapper = new GenericRowMapper<>(Attribute.class);
        return jdbcTemplate.query(
                "select attr.attribute_id. attr.attribute_name, attr_val.value  from from attribute attr inner join attribute_value attr_val on attr.attribute_id = attr_val.attribute_id",
                rowMapper);
    }

    public boolean update(AttributeRequestDTO attributeRequestDTO) {
        int rowAttr = jdbcTemplate.update("update attribute set attribute_name = ? where attribute_id = ?",
                attributeRequestDTO.getAttributeName(),
                new Object[] { UUIDUtil.uuidToBytes(attributeRequestDTO.getAttributeId()) });
        int row = jdbcTemplate.update("update attribute_value set value = ? where attribute_id = ?",
                attributeRequestDTO.getValue(),
                new Object[] { UUIDUtil.uuidToBytes(attributeRequestDTO.getAttributeId()) });
        return row > 0 && rowAttr > 0;
    }

    public boolean delete(UUID id) {
        int row = jdbcTemplate.update("delete from attribute_value where attribute_id = ?",
                new Object[] { UUIDUtil.uuidToBytes(id) });
        return row > 0;
    }

}
