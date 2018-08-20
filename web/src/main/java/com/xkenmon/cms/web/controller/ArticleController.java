package com.xkenmon.cms.web.controller;

import com.xkenmon.cms.dao.entity.Article;
import com.xkenmon.cms.web.annotation.AccessCount;
import com.xkenmon.cms.web.annotation.AccessLogger;
import com.xkenmon.cms.web.annotation.CountType;
import com.xkenmon.cms.web.dto.ModelResult;
import com.xkenmon.cms.web.service.ArticleWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    @AccessCount(countType = CountType.ARTICLE, siteId = "#ret.model.get('article').articleSiteId", contentId = "#id")
    @RequestMapping("{id}")
    public ModelAndView show(@PathVariable("id") Integer id, ModelAndView model) {

        ModelResult result = webService.getArticleModel(id);

        if (result == null) {
            model.setStatus(HttpStatus.NOT_FOUND);
            return model;
        }
        model.addAllObjects(result.getMap());

        Article article = (Article) result.get("article");

        //If skin = null, put default skin
        if (article.getArticleSkin() == null) {
            logger.error("{} skin name is null!", article.getArticleTitle());
            model.setStatus(HttpStatus.NOT_FOUND);
            return model;
        }

        model.addObject("BaseSkinPath", article.getArticleSkin().split("/")[0]);

        webService.addArticleHit(article);

        model.setViewName(article.getArticleSkin());

        return model;
    }
}
