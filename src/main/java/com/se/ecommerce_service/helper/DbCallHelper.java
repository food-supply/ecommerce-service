package com.se.ecommerce_service.helper;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

@Component
public class DbCallHelper {
    private final JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public DbCallHelper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = jdbcTemplate.getDataSource();
    }

    // Call Stored Procedure
    public Map<String, Object> callProcedure(String procName, Map<String, Object> inParams, Map<String, SqlParameter> outParams) {
        
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
            .withProcedureName(procName);

        if (outParams != null) {
            call.declareParameters(outParams.values().toArray(new SqlParameter[0]));
        }

        return call.execute(inParams);
    }


    // Call Function
    public Object callFunction(String functionName, Map<String, Object> inParams, int returnSqlType) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withFunctionName(functionName)
                .declareParameters(new SqlOutParameter("return", returnSqlType));

        MapSqlParameterSource params = new MapSqlParameterSource();
        inParams.forEach(params::addValue);

        return jdbcCall.executeFunction(Object.class, params);
    }

    // Query View
    public <T> T queryView(String sql, Object[] args, RowMapper<T> rowMapper) {
        return jdbcTemplate.queryForObject(sql, args, rowMapper);
    }

    // Query View (multiple rows)
    public <T> java.util.List<T> queryViewList(String sql, Object[] args, RowMapper<T> rowMapper) {
        return jdbcTemplate.query(sql, args, rowMapper);
    }
}
