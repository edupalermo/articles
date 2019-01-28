package org.article.service;

import org.article.entity.SystemUserEntity;
import org.article.persistence.SystemUserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SystemUserService {

    @Autowired
    private SystemUserPersistence systemUserPersistence;

    public Optional<SystemUserEntity> findByLogin(String login) {
        return systemUserPersistence.findByLogin(login);
    }
}
