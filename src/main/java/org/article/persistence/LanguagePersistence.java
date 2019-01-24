package org.article.persistence;

import org.article.entity.LanguageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class LanguagePersistence {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<LanguageEntity> rowMapper = new RowMapper<LanguageEntity>() {
        @Override
        public LanguageEntity mapRow(ResultSet rs, int i) throws SQLException {
            LanguageEntity languageEntity = new LanguageEntity();
            languageEntity.setId(rs.getLong("ID"));
            languageEntity.setName(rs.getString("NAME"));
            return languageEntity;
        }
    };

    public List<LanguageEntity> get() {
        return this.jdbcTemplate.query("SELECT ID, NAME FROM LANGUAGE", rowMapper);
    }

}
