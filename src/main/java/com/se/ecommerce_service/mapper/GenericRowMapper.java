package com.se.ecommerce_service.mapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import com.se.ecommerce_service.helper.UUIDUtil;

public class GenericRowMapper<T> implements RowMapper<T>{
    private final Class<T> clazz;

    public GenericRowMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    private String toCamelCase(String snake) {
        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;
        for (char c : snake.toCharArray()) {
            if (c == '_') {
                nextUpper = true;
            } else {
                result.append(nextUpper ? Character.toUpperCase(c) : c);
                nextUpper = false;
            }
        }
        return result.toString();
    }
    
    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                String column = meta.getColumnLabel(i);
                Object value = rs.getObject(column);
                String fieldName = toCamelCase(column);

                Field field;
                try {
                    field = clazz.getDeclaredField(fieldName);
                } catch (NoSuchFieldException e) {
                    continue;
                }

                field.setAccessible(true);
                
                if (field.getType().equals(UUID.class) && value instanceof byte[]) {
                    field.set(obj, UUIDUtil.bytesToUuid((byte[]) value));
                } else {
                    field.set(obj, value);
                }
            }

            return obj;
        } catch (Exception e) {
            throw new RuntimeException("Failed to map row to " + clazz.getSimpleName(), e);
        }
    }
}
