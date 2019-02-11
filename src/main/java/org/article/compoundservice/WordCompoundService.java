package org.article.compoundservice;

import org.article.controller.bean.ArticleStatisticsBean;
import org.article.controller.bean.WordCountBean;
import org.article.entity.ArticleEntity;
import org.article.entity.SystemUserEntity;
import org.article.entity.WordEntity;
import org.article.service.SystemUserService;
import org.article.service.WordService;
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

    @Autowired private WordService wordService;
    @Autowired private SystemUserService systemUserService;

    public List<WordCountBean> getUntreatedWords(ArticleEntity articleEntity, String systemUserLogin) {
        SystemUserEntity systemUserEntity = systemUserService.findByLogin(systemUserLogin).orElseThrow(() -> new IllegalStateException(String.format("Unknown user [%s]", systemUserLogin)));
        return getUntreatedWords(articleEntity, wordService.findBySystemUser(systemUserEntity));

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

    private List<String> getWords(ArticleEntity articleEntity) {
        return Arrays.stream(articleEntity.getContent().split("\\s|“|”|\\.|,|’|–|'|‘|:|\\(|\\)"))
                .filter(s -> (s != null) && (s.trim().length() > 0))
                .map(s -> s.trim().toUpperCase())
                .collect(Collectors.toList());
    }

    private List<String> removeKnownWords(List<String> allWords, Long languageId, String systemUserLogin) {
        List<WordEntity> knownWords = wordService.findKnownWords(languageId, systemUserLogin);

        return 

        return Arrays.stream(articleEntity.getContent().split("\\s|“|”|\\.|,|’|–|'|‘|:|\\(|\\)"))
                .filter(s -> (s != null) && (s.trim().length() > 0))
                .map(s -> s.trim().toUpperCase())
                .collect(Collectors.toList());
    }

    public ArticleStatisticsBean gatherStatistics(ArticleEntity articleEntity, String systemUserLogin) {

        List<String> words = getWords(articleEntity);

        ArticleStatisticsBean articleStatisticsBean = new ArticleStatisticsBean();
        articleStatisticsBean.setId(articleEntity.getId());
        articleStatisticsBean.setTitle(articleEntity.getTitle());
        articleStatisticsBean.setTotalWords(words.size());
        articleStatisticsBean.setCoveredWords();
        articleStatisticsBean.setUnknownWords();
        articleStatisticsBean.setCreated(articleEntity.getCreated());

        return articleStatisticsBean;
    }

}
