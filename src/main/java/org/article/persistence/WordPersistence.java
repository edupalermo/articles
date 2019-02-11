package org.article.persistence;

import org.article.controller.bean.WordCountBean;
import org.article.entity.ArticleEntity;
import org.article.entity.LanguageEntity;
import org.article.entity.SystemUserEntity;
import org.article.entity.WordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
        wordEntity.setCreated(rs.getTimestamp("CREATED").toLocalDateTime());
        return wordEntity;
    };

    public List<WordEntity> find(SystemUserEntity systemUserEntity) {
        Object[] args = new Object[] {systemUserEntity.getId()};
        return jdbcTemplate.query("SELECT ID, LANGUAGE_ID, SYSTEM_USER_ID, WORD, CREATED FROM WORD where SYSTEM_USER_ID = ?", args, rowMapper);
    }

    public Optional<WordEntity> find(LanguageEntity languageEntity, SystemUserEntity systemUserEntity, String word) {
        Object[] args = new Object[] {languageEntity.getId(), systemUserEntity.getId(), word};
        return Optional.of(jdbcTemplate.queryForObject("SELECT ID, LANGUAGE_ID, SYSTEM_USER_ID, WORD, CREATED FROM WORD where LANGUAGE_ID = ? and SYSTEM_USER_ID = ? and WORD = ?", args, rowMapper));
    }

    public List<WordEntity> find(Long languageId, String systemUserLogin) {
        Object[] args = new Object[] {languageId, systemUserLogin};
        return jdbcTemplate.query("SELECT w.* FROM " +
                " WORD w " +
                " INNER JOIN SYSTEM_USER u ON w.SYSTEM_USER_ID = u.ID " +
                " WHERE w.LANGUAGE_ID = ? and u.LOGIN = ?", args, rowMapper);
    }

    public WordEntity save(WordEntity wordEntity) {
        Object[] args = new Object[] { wordEntity.getLanguageEntity().getId(), wordEntity.getSystemUserEntity().getId(), wordEntity.getWord()};
        jdbcTemplate.update("INSERT INTO WORD (LANGUAGE_ID, SYSTEM_USER_ID, WORD) VALUES (?, ?, ?)", args);
        return find(wordEntity.getLanguageEntity(), wordEntity.getSystemUserEntity(), wordEntity.getWord()).orElseThrow(() -> new RuntimeException("Fail to insert into table"));
    }

}
