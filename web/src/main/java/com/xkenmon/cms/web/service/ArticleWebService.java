package com.xkenmon.cms.web.service;

import com.xkenmon.cms.common.constant.AccessStatus;
import com.xkenmon.cms.dao.entity.Article;
import com.xkenmon.cms.dao.entity.Category;
import com.xkenmon.cms.dao.entity.Site;
import com.xkenmon.cms.dao.entity.Tag;
import com.xkenmon.cms.dao.mapper.*;
import com.xkenmon.cms.web.dto.ModelResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author bigmeng
 */
@Service
public class ArticleWebService {
    private final Logger logger = LoggerFactory.getLogger(ArticleWebService.class);

    private final
    SiteMapper siteMapper;

    private final
    ArticleMapper articleMapper;

    private final
    CategoryMapper categoryMapper;

    private final
    FileMapper webFileMapper;


    private final TagMapper tagMapper;


//    private CmsCache<Integer, ModelResult> articleModelCache = new LRUCache<>(128);

    public ArticleWebService(SiteMapper siteMapper
            , ArticleMapper articleMapper
            , CategoryMapper categoryMapper
            , FileMapper webFileMapper
            , TagMapper tagMapper) {
        this.siteMapper = siteMapper;
        this.articleMapper = articleMapper;
        this.categoryMapper = categoryMapper;
        this.webFileMapper = webFileMapper;
        this.tagMapper = tagMapper;
    }


    @Cacheable(value = "articleModel", key = "#id", sync = true)
    public ModelResult getArticleModel(Integer id) {

        Article article = articleMapper.selectById(id);
        //审核未通过或未到发布时间
        boolean valid = AccessStatus.ACCESS.equals(article.getArticleStatus());
        if (!valid) {
            return null;
        }

        ModelResult result = new ModelResult();

        //Get article's site
        Site site = siteMapper.selectById(article.getArticleSiteId());

        //Get article's category
        Category category = categoryMapper.selectById(article.getArticleCategoryId());

        //Get article's tags
        List<Tag> tagList = tagMapper.selectTagsByArticleId(article.getArticleId());
        //Get 0 level categorise in this site (displayed above the homepage of the website)
        //Each level 0 category into category tree


        List<String> fileKeys = webFileMapper.selectFileKeyByArticleId(article.getArticleId());

        result.put("category", category)
                .put("article", article)
                .put("fileKeys", fileKeys)
                .put("tagList", tagList)
                .put("site", site);

        return result;
    }

    public void addArticleHit(Article article) {
        //hit add
        if (article.getArticleHit() == null) {
            article.setArticleHit(0);
        }

        article.setArticleHit(article.getArticleHit() + 1);
        articleMapper.updateById(article);
    }
}
