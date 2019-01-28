package org.article.service;

import org.article.entity.LanguageEntity;
import org.article.persistence.LanguagePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService {

    @Autowired
    private LanguagePersistence languagePersistence;

    public List<LanguageEntity> findAll() {
        return languagePersistence.findAll();
    }

    public Optional<LanguageEntity> findById(Long id) {
        return languagePersistence.findById(id);
    }
}
