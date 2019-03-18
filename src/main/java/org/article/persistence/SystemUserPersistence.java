package org.article.persistence;

import org.article.entity.SystemUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class SystemUserPersistence {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<SystemUserEntity> rowMapper = new RowMapper<SystemUserEntity>() {
        @Override
        public SystemUserEntity mapRow(ResultSet rs, int i) throws SQLException {
            SystemUserEntity systemUserEntity = new SystemUserEntity();
            systemUserEntity.setId(rs.getLong("ID"));
            systemUserEntity.setLogin(rs.getString("LOGIN"));
            systemUserEntity.setPassword(rs.getString("PASSWORD"));
            return systemUserEntity;
        }
    };

    public Optional<SystemUserEntity> findByLogin(String login) {
        String query = "SELECT ID, LOGIN, PASSWORD FROM TBL_SYSTEM_USER WHERE LOGIN = ?";
        Object[] parameters = new Object[]{login};
        return Optional.ofNullable(this.jdbcTemplate.queryForObject(query, rowMapper, parameters));
    }

    public Optional<SystemUserEntity> findById(Long id) {
        String query = "SELECT ID, LOGIN, PASSWORD FROM TBL_SYSTEM_USER WHERE ID = ?";
        Object[] parameters = new Object[]{id};
        return Optional.ofNullable(this.jdbcTemplate.queryForObject(query, rowMapper, parameters));
    }
}
