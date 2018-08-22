package com.xkenmon.cms.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xkenmon.cms.common.constant.AccessStatus;
import com.xkenmon.cms.common.constant.CMSContentType;
import com.xkenmon.cms.dao.entity.Article;
import com.xkenmon.cms.dao.entity.Category;
import com.xkenmon.cms.dao.entity.Site;
import com.xkenmon.cms.dao.mapper.ArticleMapper;
import com.xkenmon.cms.dao.mapper.CategoryMapper;
import com.xkenmon.cms.dao.mapper.SiteMapper;
import com.xkenmon.cms.web.dto.ModelResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author bigmeng
 */
@Service
public class SiteWebService {
    private static final Logger logger = LoggerFactory.getLogger(SiteWebService.class);

    private final
    SiteMapper siteMapper;

    private final
    ArticleMapper articleMapper;

    private final
    CategoryMapper categoryMapper;

    @Value("${qiniu.cdnDomain}")
    private String cdnDomain;

    public SiteWebService(SiteMapper siteMapper
            , ArticleMapper articleMapper
            , CategoryMapper categoryMapper) {
        this.siteMapper = siteMapper;
        this.articleMapper = articleMapper;
        this.categoryMapper = categoryMapper;
    }

    @Cacheable(value = "siteModel", key = "#siteUrl", sync = true)
    public ModelResult getSiteModel(String siteUrl) {

        ModelResult result = new ModelResult();

        //---------------------------------------default properties start----------------------------------------------//

        //get Site object
        Site site = siteMapper.selectByUrl(siteUrl);
        if (site == null) {
            return null;
        }

        //Get 0 level categorise in this site (displayed above the homepage of the website)

        //Each level 0 category into category tree


        //Get a list of categories to put on the home page (with short article info)
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<Category>()
                .eq("category_type", CMSContentType.HOMEPAGE_CATEGORY_TYPE)
                .and(w -> w.eq("category_site_id", site.getSiteId()));
        List<Category> indexCategoryList = categoryMapper.selectList(categoryQueryWrapper);


        //Get the list of articles to place on the homepage
        Map<String, Object> articleEqualMap = new HashMap<>(3);
        articleEqualMap.put("article_type", CMSContentType.HOMEPAGE_ARTICLE_TYPE);
        articleEqualMap.put("article_site_id", site.getSiteId());
        articleEqualMap.put("article_status", AccessStatus.ACCESS);
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<Article>().allEq(articleEqualMap);
        List<Article> indexArticleList = articleMapper.selectList(articleQueryWrapper);

        //The default templates needs the data
        result
                .put("site", site)
                .put("indexCategoryList", indexCategoryList)
                .put("indexArticleList", indexArticleList)
                .put("cdnDomain", cdnDomain)
                .put("baseSkinPath", site.getSiteSkin().split("/")[0]);

        //---------------------------------------custom properties start----------------------------------------------//


        /*
        The custom type category Need to pass to the home page
        key - type
        value - list of (category)
               map<"type",
                   list[
                       (category)->category type is "type"
                    ]
                >
        */
        Map<String, Object> categoryQueryMap = new HashMap<>();
        categoryQueryMap.put("category_in_homepage", true);
        categoryQueryMap.put("category_site_id", site.getSiteId());
        categoryQueryMap.put("category_status", AccessStatus.ACCESS);
        result.getMap().putAll(categoryMapper.selectList(new QueryWrapper<Category>().allEq(categoryQueryMap))
                .stream()
                .collect(Collectors.groupingBy(Category::getCategoryType)));

        articleEqualMap.clear();
        articleEqualMap.put("article_in_homepage", true);
        articleEqualMap.put("article_site_id", site.getSiteId());
        articleEqualMap.put("article_status", AccessStatus.ACCESS);

        result.getMap()
                .putAll(articleMapper.selectList(new QueryWrapper<Article>().allEq(articleEqualMap))
                        .parallelStream()
                        .collect(Collectors.groupingBy(Article::getArticleType)));

        return result;
    }

    public void addSiteHit(Site site) {
        Site update = new Site();
        update.setSiteId(site.getSiteId());
        update.setSiteHit(site.getSiteHit() == null ? 1 : site.getSiteHit() + 1);
        siteMapper.updateById(update);
    }
}
