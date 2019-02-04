package org.article.controller.command;

public class WordUpdateCommand {
    private Long articleId;
    private String[] word;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String[] getWord() {
        return word;
    }

    public void setWord(String[] word) {
        this.word = word;
    }
}
