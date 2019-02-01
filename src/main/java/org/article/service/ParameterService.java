package org.article.service;

import org.article.entity.ParameterEntity;
import org.article.persistence.ParameterPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ParameterService {

    @Autowired
    private ParameterPersistence parameterPersistence;

    public Optional<ParameterEntity> findByKey(String key) {
        return parameterPersistence.findByKey(key);
    }

}
