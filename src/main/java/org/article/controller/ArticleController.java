package org.article.controller;

import org.article.Application;
import org.article.compoundservice.WordCompoundService;
import org.article.controller.bean.ArticleStatisticsBean;
import org.article.controller.command.ArticleCreateCommand;
import org.article.controller.command.ArticleListCommand;
import org.article.controller.command.WordUpdateCommand;
import org.article.entity.ArticleEntity;
import org.article.entity.LanguageEntity;
import org.article.entity.SystemUserEntity;
import org.article.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private ArticleService articleService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private WordService wordService;

    @Autowired
    private WordCompoundService wordCompoundService;

    private static final String DEFAULT_LOGIN = "palermo";

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("form", new ArticleCreateCommand());
        return "article/create";
    }

    @PostMapping("/create")
    public ModelAndView create(Model model, ArticleCreateCommand articleCreateCommand) {
        logger.info("Language: " + articleCreateCommand.getLanguageId());
        articleService.save(map(articleCreateCommand));
        model.addAttribute("form", articleCreateCommand);
        return new ModelAndView("redirect:/article/list");
    }

    @ModelAttribute("languages")
    public List<LanguageEntity> getLanguages() {
        return languageService.findAll();
    }

    public ArticleEntity map(ArticleCreateCommand articleCreateCommand) {
        LanguageEntity languageEntity = languageService.findById(articleCreateCommand.getLanguageId())
                .orElseThrow(() -> new IllegalStateException(String.format("It was not found a Language with id [%s]", articleCreateCommand.getLanguageId())));

        SystemUserEntity systemUserEntity = systemUserService.findByLogin(DEFAULT_LOGIN)
                .orElseThrow(() -> new IllegalStateException(String.format("It was not found a User with login [%s]", DEFAULT_LOGIN)));

        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setContent(articleCreateCommand.getContent());
        articleEntity.setLanguageEntity(languageEntity);
        articleEntity.setReference(articleCreateCommand.getReference());
        articleEntity.setIdPublic(articleCreateCommand.getIsPublic());
        articleEntity.setTitle(articleCreateCommand.getTitle());
        articleEntity.setSystemUserEntity(systemUserEntity);

        return articleEntity;
    }

    @GetMapping("/list")
    public String getList(Model model, ArticleListCommand articleListCommand) {
        List<ArticleStatisticsBean> articleStatistics = articleService.findByLanguageIdSystemUserLogin(articleListCommand.getLanguageId(), DEFAULT_LOGIN)
                .stream().map(articleEntity -> wordCompoundService.gatherStatistics(articleEntity, DEFAULT_LOGIN)).collect(Collectors.toList());

        model.addAttribute("articles", articleStatistics);
        model.addAttribute("form", new ArticleListCommand());
        return "article/list";
    }

    @PostMapping("/list")
    public String postList(Model model, ArticleListCommand articleListCommand) {
        List<ArticleStatisticsBean> articleStatistics = articleService.findByLanguageIdSystemUserLogin(articleListCommand.getLanguageId(), DEFAULT_LOGIN)
                .stream().map(articleEntity -> wordCompoundService.gatherStatistics(articleEntity, DEFAULT_LOGIN)).collect(Collectors.toList());

        model.addAttribute("articles", articleStatistics);
        model.addAttribute("form", new ArticleListCommand());
        return "article/list";
    }

    @GetMapping("/show")
    public String show(Model model, @ModelAttribute("id") Long articleId) {

        ArticleEntity articleEntity = articleService.findById(articleId)
                .orElseThrow(() -> new IllegalStateException(String.format("It was not found an article with id [%s]", articleId)));

        model.addAttribute("article", articleEntity);
        model.addAttribute("words", wordCompoundService.getUntreatedWords(articleEntity, DEFAULT_LOGIN));
        return "article/show";
    }

    @PostMapping("/word/add")
    public String addWords(Model model, WordUpdateCommand wordUpdateCommand) {

        logger.info("Word List: " + wordUpdateCommand.getWords());

        ArticleEntity articleEntity = articleService.findById(wordUpdateCommand.getArticleId())
                .orElseThrow(() -> new IllegalStateException(String.format("It was not found an article with id [%s]", wordUpdateCommand.getArticleId())));

        SystemUserEntity systemUserEntity = systemUserService.findByLogin(DEFAULT_LOGIN)
                .orElseThrow(() -> new IllegalStateException(String.format("It was not found an User with id [%s]", DEFAULT_LOGIN)));

        wordService.save(wordUpdateCommand.getWords(), articleEntity.getLanguageEntity(), systemUserEntity);

        model.addAttribute("article", articleEntity);
        model.addAttribute("words", wordCompoundService.getUntreatedWords(articleEntity, DEFAULT_LOGIN));
        return "article/show";
    }

}
