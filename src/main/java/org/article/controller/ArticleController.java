package org.article.controller;

import org.article.Application;
import org.article.controller.bean.ArticleCreateCommand;
import org.article.service.ArticleService;
import org.article.service.LanguageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private ArticleService articleService;

    @Autowired
    private LanguageService languageService;

    @GetMapping("/create")
    public String create(Model model) {
        populateFormData(model);
        return "article/create";
    }

    @PostMapping("/create")
    public String create(Model model, ArticleCreateCommand articleCreateCommand) {
        logger.info("Language: " + articleCreateCommand.getLanguageId());
        populateFormData(model, articleCreateCommand);
        return "article/create";
    }

    private void populateFormData(Model model) {
        populateFormData(model, new ArticleCreateCommand());
    }

    private void populateFormData(Model model, ArticleCreateCommand articleCreateCommand) {
        model.addAttribute("form", articleCreateCommand);
        model.addAttribute("languages", languageService.get());
    }

    @ModelAttribute("languages")
    public String[] getLanguages() {
        return new String[] {
                "Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"
        };
    }
}
