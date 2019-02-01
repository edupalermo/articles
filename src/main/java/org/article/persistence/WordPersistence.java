package org.article.persistence;

import org.article.entity.SystemUserEntity;
import org.article.entity.WordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class WordPersistence {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LanguagePersistence languagePersistence;

    @Autowired
    private SystemUserPersistence systemUserPersistence;

    private RowMapper<WordEntity> rowMapper = (ResultSet rs, int i) -> {
        WordEntity wordEntity = new WordEntity();
        wordEntity.setId(rs.getLong("ID"));
        wordEntity.setLanguageEntity(languagePersistence.findById(rs.getLong("LANGUAGE_ID")).orElseThrow(() -> new RuntimeException("Inconsistency")));
        wordEntity.setSystemUserEntity(systemUserPersistence.findById(rs.getLong("SYSTEM_USER_ID")).orElseThrow(() -> new RuntimeException("Inconsistency")));
        wordEntity.setWord(rs.getString("WORD"));
        wordEntity.setCreated(rs.getTimestamp("WORD").toLocalDateTime());
        return wordEntity;
    };

    public List<WordEntity> findBySystemUser(SystemUserEntity systemUserEntity) {
        Object[] args = new Object[] {systemUserEntity.getId()};
        return jdbcTemplate.query("SELECT ID, LANGUAGE_ID, SYSTEM_USER_ID, WORD FROM WORD where SYSTEM_USER_ID = ?", args, rowMapper);
    }
}
