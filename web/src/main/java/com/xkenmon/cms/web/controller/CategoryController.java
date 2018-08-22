package com.xkenmon.cms.web.controller;

import com.xkenmon.cms.dao.entity.Category;
import com.xkenmon.cms.web.annotation.AccessCount;
import com.xkenmon.cms.web.annotation.AccessLogger;
import com.xkenmon.cms.web.annotation.CountType;
import com.xkenmon.cms.web.dto.ModelResult;
import com.xkenmon.cms.web.service.CategoryWebService;
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
@RequestMapping("/view/category/")
public class CategoryController {
    private final CategoryWebService webService;

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    public CategoryController(CategoryWebService webService) {
        this.webService = webService;
    }

    @AccessCount(countType = CountType.CATEGORY, contentId = "#id", siteId = "#ret.model.get('category').categorySiteId")
    @AccessLogger
    @RequestMapping("{id}")
    public ModelAndView show(@PathVariable("id") Integer id, ModelAndView model) {
        ModelResult result = webService.getCategoryModel(id);

        if (result == null) {
            model.setStatus(HttpStatus.NOT_FOUND);
            return model;
        }

        //Add result to module
        model.addAllObjects(result.getMap());

        Category category = (Category) result.get("category");

        //If skin = null, put default skin
        if (category.getCategorySkin() == null) {
            logger.error("{} skin name is null", category.getCategoryTitle());
        }

        webService.addCategoryHit(category);
        //Return to the site's skin view, for example : default-category
        model.setViewName(category.getCategorySkin());

        return model;
    }
}
