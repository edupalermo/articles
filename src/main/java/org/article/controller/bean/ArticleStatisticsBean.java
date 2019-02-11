package org.article.controller.bean;

import java.time.LocalDateTime;

public class ArticleStatisticsBean {

    private Long id;
    private String title;
    private LocalDateTime created;
    private int totalWords;
    private int unknownWords;
    private int coveredWords;

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

    public int getTotalWords() {
        return totalWords;
    }

    public void setTotalWords(int totalWords) {
        this.totalWords = totalWords;
    }

    public int getUnknownWords() {
        return unknownWords;
    }

    public void setUnknownWords(int unknownWords) {
        this.unknownWords = unknownWords;
    }

    public int getCoveredWords() {
        return coveredWords;
    }

    public void setCoveredWords(int coveredWords) {
        this.coveredWords = coveredWords;
    }
}
