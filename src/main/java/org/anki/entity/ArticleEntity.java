package org.anki.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleEntity {
    private Long id;
    private LanguageEntity languageEntity;
    private UserEntity userEntity;
    private String title;
    private String content;
    private String reference;
    private Boolean isPublic;
    private LocalDateTime created;
}
