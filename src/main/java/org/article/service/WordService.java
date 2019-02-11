package org.article.service;

import org.article.controller.bean.ArticleStatisticsBean;
import org.article.entity.ArticleEntity;
import org.article.entity.LanguageEntity;
import org.article.entity.SystemUserEntity;
import org.article.entity.WordEntity;
import org.article.persistence.WordPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordService {

    @Autowired
    private WordPersistence wordPersistence;

    public List<WordEntity> findBySystemUser(SystemUserEntity systemUserEntity) {
        return wordPersistence.find(systemUserEntity);
    }

    public List<WordEntity> findKnownWords(Long languageId, String systemUserLogin) {
        return wordPersistence.find(systemUserEntity);
    }

    public List<WordEntity> save(String[] words, LanguageEntity languageEntity, SystemUserEntity systemUserEntity) {
        return Arrays.stream(words).map(word -> {
            WordEntity wordEntity = new WordEntity();
            wordEntity.setWord(word);
            wordEntity.setLanguageEntity(languageEntity);
            wordEntity.setSystemUserEntity(systemUserEntity);
            return wordPersistence.save(wordEntity);
        }).collect(Collectors.toList());
    }


    private List<WordEntity> save(List<WordEntity> wordEntityList) {
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
