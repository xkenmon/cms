package com.xkenmon.cms.web.controller;

import com.xkenmon.cms.dao.entity.Site;
import com.xkenmon.cms.web.annotation.AccessCount;
import com.xkenmon.cms.web.annotation.AccessLogger;
import com.xkenmon.cms.web.annotation.CountType;
import com.xkenmon.cms.web.dto.ModelResult;
import com.xkenmon.cms.web.service.SiteWebService;
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
public class SiteController {

    private final SiteWebService webService;
    private static final Logger logger = LoggerFactory.getLogger(SiteController.class);

    @Autowired
    public SiteController(SiteWebService webService) {
        this.webService = webService;
    }


    @AccessCount(countType = CountType.SITE,
            contentId = "#ret.model.get('site').siteId",
            siteId = "#ret.model.get('site').siteId")
    @AccessLogger
    @RequestMapping("/{siteUrl}")
    public ModelAndView show(@PathVariable("siteUrl") String siteUrl, ModelAndView model) {

        ModelResult result = webService.getSiteModel(siteUrl);

        if (result == null) {
            model.setStatus(HttpStatus.NOT_FOUND);
            return model;
        }

        //Add result to module
        model.addAllObjects(result.getMap());

        Site site = (Site) result.get("site");

        //If skin = null, put default skin
        if (site.getSiteSkin() == null) {
            logger.error("{} skin name is null", site.getSiteName());
        }

        model.addObject("BaseSkinPath", site.getSiteSkin().split("/")[0]);

        webService.addSiteHit(site);

        //Return to the site's skin view, for example : default-site
        model.setViewName(site.getSiteSkin());

        return model;
    }

    /**
     * map "/" to "/index"
     *
     * @return redirect
     */
    @RequestMapping("/")
    public String index() {
        return "redirect:/index";
    }
}
