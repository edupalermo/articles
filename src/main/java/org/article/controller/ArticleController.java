package org.article.controller;

import org.article.Application;
import org.article.controller.bean.ArticleCreateCommand;
import org.article.controller.command.ArticleCreateCommand;
import org.article.entity.ArticleEntity;
import org.article.entity.LanguageEntity;
import org.article.entity.SystemUserEntity;
import org.article.service.ArticleService;
import org.article.service.LanguageService;
import org.article.service.ParameterService;
import org.article.service.SystemUserService;
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
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

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

    private static final String DEFAULT_LOGIN = "palermo";

    @GetMapping("/create")
    public String create(Model model) {
        populateFormData(model);
        return "article/create";
    }

    @PostMapping("/create")
    public ModelAndView create(Model model, ArticleCreateCommand articleCreateCommand) {
        logger.info("Language: " + articleCreateCommand.getLanguageId());
        articleService.save(map(articleCreateCommand));
        populateFormData(model, articleCreateCommand);
        return new ModelAndView("redirect:/article/list");
    }

    private void populateFormData(Model model) {
        populateFormData(model, new ArticleCreateCommand());
    }

    private void populateFormData(Model model, ArticleCreateCommand articleCreateCommand) {
        model.addAttribute("form", articleCreateCommand);
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
    public String list(Model model) {
        model.addAttribute("articles", articleService.findAll());
        return "article/list";
    }

    @GetMapping("/show")
    public String show(Model model, @ModelAttribute("id") Long articleId) {
        model.addAttribute("article", articleService.findById(articleId)
                .orElseThrow(() -> new IllegalStateException(String.format("It was not found an article with id [%s]", articleId.toString()))));
        return "article/show";
    }
}
