package com.xkenmon.cms.web.controller;

import com.xkenmon.cms.dao.entity.Article;
import com.xkenmon.cms.web.annotation.AccessCount;
import com.xkenmon.cms.web.annotation.AccessLogger;
import com.xkenmon.cms.web.annotation.CountParam;
import com.xkenmon.cms.web.annotation.CountType;
import com.xkenmon.cms.web.dto.ModelResult;
import com.xkenmon.cms.web.service.ArticleWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author bigmeng
 */
@Controller
@RequestMapping("/view/article/")
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleWebService webService;

    @Autowired
    public ArticleController(ArticleWebService webService) {
        this.webService = webService;
    }

    @AccessLogger()
    @AccessCount(value = CountType.ARTICLE)
    @RequestMapping("{id}")
    public String show(@CountParam @PathVariable("id") Integer id, Model model) {

        ModelResult result = webService.getArticleModel(id);

        if (result == null) {
            return "404";
        }

        model.addAttribute("result", result);

        Article article = (Article) result.get("article");

        //If skin = null, put default skin
        if (article.getArticleSkin() == null) {
            logger.error("{} skin name is null!", article.getArticleTitle());
        }

        model.addAttribute("BaseSkinPath", article.getArticleSkin().split("/")[0]);

        webService.addArticleHit(article);

        //Return to the site's skin view, for example : default-article
        return article.getArticleSkin();
    }
}
