package org.article.controller;

import org.article.Application;
import org.article.controller.bean.ArticleCreateBean;
import org.article.service.ArticleService;
import org.article.service.LanguageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String create(Model model, ArticleCreateBean articleCreateBean) {
        logger.info("Language: " + articleCreateBean.getLanguageId());
        populateFormData(model);
        return "article/create";
    }

    @GetMapping("/list")
    public String list(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    private void populateFormData(Model model) {
        model.addAttribute("languages", languageService.get());
    }

}
