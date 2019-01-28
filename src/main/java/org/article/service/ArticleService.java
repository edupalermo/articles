package org.article.service;

import org.article.entity.ArticleEntity;
import org.article.persistence.ArticlePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticlePersistence articlePersistence;

    public List<ArticleEntity> findAll() {
        return articlePersistence.findAll();
    }

    public ArticleEntity save(ArticleEntity articleBean) {
        return articlePersistence.save(articleBean);
    }

}
