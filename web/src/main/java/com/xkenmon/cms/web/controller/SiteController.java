package com.xkenmon.cms.web.controller;

import com.xkenmon.cms.dao.entity.Site;
import com.xkenmon.cms.web.annotation.AccessCount;
import com.xkenmon.cms.web.annotation.AccessLogger;
import com.xkenmon.cms.web.annotation.CountParam;
import com.xkenmon.cms.web.annotation.CountType;
import com.xkenmon.cms.web.dto.ModelResult;
import com.xkenmon.cms.web.service.SiteWebService;
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
public class SiteController {

    private final SiteWebService webService;
    private static final Logger logger = LoggerFactory.getLogger(SiteController.class);

    @Autowired
    public SiteController(SiteWebService webService) {
        this.webService = webService;
    }


    @AccessCount(CountType.SITE)
    @AccessLogger
    @RequestMapping("/{siteUrl}")
    public String show(@CountParam @PathVariable("siteUrl") String siteUrl, Model model) {

        ModelResult result = webService.getSiteModel(siteUrl);

        if (result == null) {
            return "/error/404";
        }

        //Add result to module
        model.addAttribute("result", result);

        Site site = (Site) result.get("site");

        //If skin = null, put default skin
        if (site.getSiteSkin() == null) {
            logger.error("{} skin name is null", site.getSiteName());
        }

        model.addAttribute("BaseSkinPath", site.getSiteSkin().split("/")[0]);

        webService.addSiteHit(site);
        //Return to the site's skin view, for example : default-site
        return site.getSiteSkin();
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
