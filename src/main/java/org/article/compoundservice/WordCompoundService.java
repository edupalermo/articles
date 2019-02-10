package org.article.compoundservice;

import org.article.controller.bean.WordCountBean;
import org.article.entity.ArticleEntity;
import org.article.entity.WordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class WordCompoundService {

    @Autowired
    private

    public List<WordCountBean> getUntreatedWords(ArticleEntity articleEntity) {

    }

    private List<WordCountBean> getUntreatedWords(ArticleEntity articleEntity, List<WordEntity> wordEntityList) {
        return count(Arrays.stream(articleEntity.getContent().split("\\s|“|”|\\.|,|’|–|'|‘|:|\\(|\\)"))
                .filter(s -> (s != null) && (s.trim().length() > 0))
                .map(s -> s.trim().toUpperCase())
                .filter(s -> !wordEntityList.stream().map(WordEntity::getWord).filter(word -> word.equalsIgnoreCase(s)).findAny().isPresent())
                .collect(Collectors.toList()));
    }

    private List<WordCountBean> count(List<String> wordList) {
        Map<String, WordCountBean> map = new TreeMap<>();

        for (String word : wordList) {
            WordCountBean wordCountBean = map.get(word);

            if (wordCountBean == null) {
                wordCountBean = new WordCountBean();
                wordCountBean.setWord(word);
                wordCountBean.setCount(1);
                map.put(word, wordCountBean);
            }
            else {
                wordCountBean.setCount(wordCountBean.getCount() + 1);
            }
        }

        return map.values().stream().sorted(Comparator.comparing(WordCountBean::getCount).reversed()).collect(Collectors.toList());
    }
}
