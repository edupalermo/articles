package org.article.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleEntity {
    private Long id;
    private LanguageEntity languageEntity;
    private SystemUserEntity systemUserEntity;
    private String title;
    private String content;
    private String reference;
    private Boolean isPublic;
    private LocalDateTime created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LanguageEntity getLanguageEntity() {
        return languageEntity;
    }

    public void setLanguageEntity(LanguageEntity languageEntity) {
        this.languageEntity = languageEntity;
    }

    public SystemUserEntity getSystemUserEntity() {
        return systemUserEntity;
    }

    public void setSystemUserEntity(SystemUserEntity systemUserEntity) {
        this.systemUserEntity = systemUserEntity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIdPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
