package org.article.service;

import org.article.entity.ArticleEntity;
import org.article.entity.WordEntity;
import org.article.persistence.ArticlePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticlePersistence articlePersistence;

    public List<ArticleEntity> findAll() {
        return articlePersistence.findAll();
    }

    public List<ArticleEntity> findByLanguageIdSystemUserLogin(Long languageId, String systemUserLogin) {
        return articlePersistence.findByLanguageIdSystemUserLogin(languageId, systemUserLogin);
    }

    public ArticleEntity save(ArticleEntity articleBean) {
        return articlePersistence.save(articleBean);
    }

    public Optional<ArticleEntity> findById(Long articleId) {
        return articlePersistence.findById(articleId);
    }

}
