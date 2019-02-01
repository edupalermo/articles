package org.article.controller.bean;

import java.time.LocalDateTime;

public class ArticleBean {

    private Long id;
    private String title;
    private LocalDateTime created;
    private double covered;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public double getCovered() {
        return covered;
    }

    public void setCovered(double covered) {
        this.covered = covered;
    }
}
