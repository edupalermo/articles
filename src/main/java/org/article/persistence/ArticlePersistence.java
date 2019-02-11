package org.article.persistence;

import org.article.entity.ArticleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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

    public Optional<ArticleEntity> findById(Long articleId) {
        String sql = "SELECT ID, LANGUAGE_ID, SYSTEM_USER_ID, TITLE, PUBLIC, CONTENT, REFERENCE, CREATED FROM ARTICLE WHERE ID = ?";
        Object args[] = new Object[] {articleId};
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, args));
    }

    public List<ArticleEntity> findByLanguageIdSystemUserLogin(Long languageId, String systemUserLogin) {
        String sql = "SELECT a.ID, a.LANGUAGE_ID, a.SYSTEM_USER_ID, a.TITLE, a.PUBLIC, a.CONTENT, a.REFERENCE, a.CREATED FROM " +
                " ARTICLE a " +
                " inner join SYSTEM_USER u ON a.SYSTEM_USER_ID = u.ID " +
                " where " +
                "     a.LANGUAGE_ID = ? " +
                " and u.LOGIN = ?";
        Object args[] = new Object[] {languageId, systemUserLogin};
        return jdbcTemplate.query(sql, args, rowMapper);

    }
}
