package org.article.service;

import org.article.entity.SystemUserEntity;
import org.article.entity.WordEntity;
import org.article.persistence.WordPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordService {

    @Autowired
    private WordPersistence wordPersistence;

    public List<WordEntity> findBySystemUser(SystemUserEntity systemUserEntity) {
        return wordPersistence.find(systemUserEntity);
    }

    public List<WordEntity> save(List<WordEntity> wordEntityList) {
        return wordEntityList.stream().map((e) -> wordPersistence.save(e)).collect(Collectors.toList());
    }
}
