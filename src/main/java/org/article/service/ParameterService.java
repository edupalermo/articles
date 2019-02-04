package org.article.service;

import org.article.entity.ParameterEntity;
import org.article.persistence.ParameterPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ParameterService {

    public static final String KEY_SEPARATOR = "SEPARATOR";

    @Autowired
    private ParameterPersistence parameterPersistence;

    public ParameterEntity findByKey(String key) {
        return parameterPersistence.findByKey(key).orElseThrow(() -> new RuntimeException(String.format("Parameter with key [%s] not found.", key)));
    }

}
