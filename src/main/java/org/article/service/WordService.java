package org.article.service;

import org.article.entity.SystemUserEntity;
import org.article.entity.WordEntity;
import org.article.persistence.WordPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordService {

    @Autowired
    private WordPersistence wordPersistence;

    public List<WordEntity> findBySystemUser(SystemUserEntity systemUserEntity) {
        return wordPersistence.findBySystemUser(systemUserEntity);
    }
}
