package com.xkenmon.cms.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.xkenmon.cms.admin.exception.ApiException;
import com.xkenmon.cms.admin.service.IArticleService;
import com.xkenmon.cms.dao.entity.Article;
import com.xkenmon.cms.dao.mapper.ArticleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bigmeng
 */
@Service
public class ArticleServiceImpl implements IArticleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private final
    ArticleMapper articleMapper;

    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    private static final ImmutableList<String> DB_FIELD = ImmutableList.of(
            "article_id",
            "article_title",
            "article_type",
            "article_author",
            "article_url",
            "article_order",
            "article_site_id",
            "article_category_id",
            "article_create_time",
            "article_update_time",
            "article_thumb",
            "article_hit",
            "article_desc",
            "article_status",
            "article_content",
            "article_skin",
            "article_in_homepage",
            "article_release_time",
            "article_release_status"
    );

    @Override
    public Article selectById(Integer id) throws ApiException {
        Article article = articleMapper.selectById(id);
        if (null == article) {
            throw new ApiException(404, "article not found");
        }
        LOGGER.info("query article - id: {}, title: {}", article.getArticleId(), article.getArticleTitle());
        return article;
    }

    @Override
    public IPage<Article> queryArticleList(Integer rowsPerPage, Integer pageNumber, String orderBy, String order) throws ApiException {
        //default asc order
        boolean isDescOrder = "Desc".equalsIgnoreCase(order);

        String dbOrderBy = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, orderBy);
        if (!DB_FIELD.contains(dbOrderBy)) {
            throw new ApiException(400, "pagination error, check your orderBy(lowerCamel) param");
        }

        List<String> fieldWithContent = DB_FIELD.stream()
                .filter(e -> !"article_content".equals(e))
                .collect(Collectors.toList());
        Wrapper<Article> wrapper = new QueryWrapper<Article>()
                // mybatis Plus 有问题
                .select(fieldWithContent.stream()
                        .map(e -> (e + " AS " + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, e)))
                        .collect(Collectors.toList())
                        .toArray(new String[]{}))
                .orderByDesc(isDescOrder, dbOrderBy)
                .orderByAsc(!isDescOrder, dbOrderBy);

        LOGGER.info(
                "query article list - pageNumber: {}, rowsPerPage: {}, orderBy: {}, order: {}",
                pageNumber, rowsPerPage, orderBy, order
        );

        return articleMapper.selectPage(new Page<>(pageNumber, rowsPerPage), wrapper);
    }

    @Override
    public Integer createArticle(Article article) throws ApiException {
        if (isInvalid(article)) {
            throw new ApiException(400, "some article field not complete");
        }
        if (articleMapper.insert(article) == 0) {
            throw new ApiException(500, "insert article failed");
        }

        LOGGER.info("insert article - id: {}, title: {}", article.getArticleId(), article.getArticleTitle());
        return article.getArticleId();
    }

    @Override
    public Article updateArticle(Article article) throws ApiException {
        if (article.getArticleId() == null || article.getArticleId() <= 0) {
            throw new ApiException(400, "must specify articleId field");
        }
        if (isInvalid(article)) {
            throw new ApiException(400, "some article field not complete");
        }
        if (articleMapper.updateById(article) == 0) {
            throw new ApiException(500, "update article failed");
        }
        LOGGER.info("update article - id: {}, title: {}", article.getArticleId(), article.getArticleTitle());
        return article;
    }

    @Override
    public Integer deleteArticle(Integer id) throws ApiException {
        int count = articleMapper.deleteById(id);
        if (count != 1) {
            throw new ApiException(400, "delete failed, article not exist");
        }
        LOGGER.info("delete article - id: {}", id);
        return count;
    }

    /**
     * 判断文章是否非法
     */
    private boolean isInvalid(Article article) {
        return Strings.isNullOrEmpty(article.getArticleAuthor()) ||
                Strings.isNullOrEmpty(article.getArticleTitle()) ||
                Strings.isNullOrEmpty(article.getArticleType()) ||
                article.getArticleId() == null ||
                article.getArticleSiteId() <= 0;
    }
}
