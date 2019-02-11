package org.article.controller.command;

public class WordUpdateCommand {
    private Long articleId;
    private String[] words;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String[] getWords() {
        return words;
    }

    public void setWords(String[] words) {
        this.words = words;
    }
}
