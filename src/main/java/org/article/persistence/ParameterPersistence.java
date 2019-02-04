package org.article.persistence;

import org.article.entity.ParameterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ParameterPersistence {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<ParameterEntity> rowMapper = (rs, i) -> {
        ParameterEntity parameterEntity = new ParameterEntity();
        parameterEntity.setId(rs.getLong("ID"));
        parameterEntity.setKey(rs.getString("KEY"));
        parameterEntity.setValue(rs.getString("VALUE"));
        return parameterEntity;
    };

    public Optional<ParameterEntity> findByKey(String key) {
        String query = "SELECT ID, KEY, VALUE FROM PARAMETER WHERE KEY = ?";
        Object[] parameters = new Object[]{key};
        return Optional.ofNullable(this.jdbcTemplate.queryForObject(query, rowMapper, parameters));
    }
}
