package org.article.persistence;

import org.article.entity.ArticleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class ArticlePersistence {

    @Autowired
    private LanguagePersistence languagePersistence;

    @Autowired
    private SystemUserPersistence systemUserPersistence;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<ArticleEntity> rowMapper = new RowMapper<ArticleEntity>() {

        @Override
        public ArticleEntity mapRow(ResultSet rs, int i) throws SQLException {
            ArticleEntity articleEntity = new ArticleEntity();
            articleEntity.setId(Long.valueOf(rs.getLong("ID")));
            articleEntity.setReference(rs.getString("REFERENCE"));
            articleEntity.setContent(rs.getString("CONTENT"));
            articleEntity.setIdPublic(Boolean.valueOf(rs.getBoolean("PUBLIC")));
            articleEntity.setCreated(rs.getTimestamp("CREATED").toLocalDateTime());
            articleEntity.setTitle(rs.getString("TITLE"));
            articleEntity.setLanguageEntity(languagePersistence.findById(rs.getLong("LANGUAGE_ID")).orElseThrow(() -> new IllegalStateException("Database inconsistency!")));
            articleEntity.setSystemUserEntity(systemUserPersistence.findById(rs.getLong("SYSTEM_USER_ID")).orElseThrow(() -> new IllegalStateException("Database inconsistency!")));

            return articleEntity;
        }
    };

    public ArticleEntity save(ArticleEntity articleEntity) {
        Object[] params = new Object[6];
        params[0] = articleEntity.getLanguageEntity().getId();
        params[1] = articleEntity.getSystemUserEntity().getId();
        params[2] = articleEntity.getTitle();
        params[3] = articleEntity.getIsPublic();
        params[4] = articleEntity.getContent();
        params[5] = articleEntity.getReference();

        jdbcTemplate.update("INSERT INTO ARTICLE (LANGUAGE_ID, SYSTEM_USER_ID, TITLE, PUBLIC, CONTENT, REFERENCE) VALUES (?, ?, ?, ?, ?, ?)", params);
        return null;
    }

    public List<ArticleEntity> findAll() {
        String sql = "SELECT ID, LANGUAGE_ID, SYSTEM_USER_ID, TITLE, PUBLIC, CONTENT, REFERENCE, CREATED FROM ARTICLE";
        return jdbcTemplate.query(sql, rowMapper);
    }

}
