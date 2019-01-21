package org.anki.persistence;

import org.anki.entity.ArticleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticlePersistence {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public ArticleEntity save(ArticleEntity articleEntity) {

        Object[] params = new Object[] { name, surname, title, new Date() };


        jdbcTemplate.update("insert into ARTICLE (LANGUAGE_ID, USER_ID, TITLE, PUBLIC, CONTENT, REFERENCE, CREATED) values (?, ?, ?, ?, ?, ?, ?)", new Object[] {});
        return null;
    }

    public List<ArticleEntity> list() {
        return null;
    }

}
