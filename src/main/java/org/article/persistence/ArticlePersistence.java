package org.article.persistence;

import org.article.entity.ArticleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticlePersistence {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public ArticleEntity save(ArticleEntity articleEntity) {

        Object[] params = new Object[6];
        params[0] = articleEntity.getLanguageEntity().getId();
        params[1] = articleEntity.getUserEntity().getId();
        params[2] = articleEntity.getTitle();
        params[3] = articleEntity.getIsPublic();
        params[4] = articleEntity.getContent();
        params[5] = articleEntity.getReference();

        jdbcTemplate.update("insert into ARTICLE (LANGUAGE_ID, USER_ID, TITLE, PUBLIC, CONTENT, REFERENCE) values (?, ?, ?, ?, ?, ?)", new Object[]{});
        return null;
    }

    public List<ArticleEntity> list() {
        return null;
    }

}
