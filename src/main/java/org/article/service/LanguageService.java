package org.article.service;

import org.article.entity.LanguageEntity;
import org.article.persistence.LanguagePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

    @Autowired
    private LanguagePersistence languagePersistence;

    public List<LanguageEntity> get() {
        return languagePersistence.get();
    }
}
