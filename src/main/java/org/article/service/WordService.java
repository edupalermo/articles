package org.article.service;

import org.article.controller.bean.WordCountBean;
import org.article.entity.ArticleEntity;
import org.article.entity.LanguageEntity;
import org.article.entity.SystemUserEntity;
import org.article.entity.WordEntity;
import org.article.persistence.WordPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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

    public List<WordEntity> saveList(SystemUserEntity systemUserEntity, LanguageEntity languageEntity, String words[]) {
        return Arrays.stream(words).map(word -> toWordEntity(systemUserEntity, languageEntity, word)).collect(Collectors.toList());
    }

    private WordEntity toWordEntity(SystemUserEntity systemUserEntity, LanguageEntity languageEntity, String word) {
        WordEntity wordEntity = new WordEntity();
        wordEntity.setWord(word);
        wordEntity.setSystemUserEntity(systemUserEntity);
        wordEntity.setLanguageEntity(languageEntity);
        return wordEntity;
    }
}
