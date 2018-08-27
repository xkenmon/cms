package com.xkenmon.cms.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import com.xkenmon.cms.admin.dto.ArticleUploadRequest;
import com.xkenmon.cms.admin.exception.ApiException;
import com.xkenmon.cms.admin.service.IArticleService;
import com.xkenmon.cms.common.constant.TableField;
import com.xkenmon.cms.dao.entity.Article;
import com.xkenmon.cms.dao.entity.File;
import com.xkenmon.cms.dao.mapper.ArticleMapper;
import com.xkenmon.cms.dao.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author bigmeng
 */
@Service
public class ArticleServiceImpl implements IArticleService {

    private final
    ArticleMapper articleMapper;

    private final
    FileMapper fileMapper;

    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper, FileMapper fileMapper) {
        this.articleMapper = articleMapper;
        this.fileMapper = fileMapper;
    }

    @Override
    @Cacheable("article")
    public Article selectById(Integer id) throws ApiException {
        Article article = articleMapper.selectById(id);
        if (null == article) {
            throw new ApiException(404, "article not found");
        }
        return article;
    }

    @Override
    @Cacheable("articleList")
    public IPage<Article> queryArticleList(Integer rowsPerPage, Integer pageNumber, String orderBy, String order, Integer sid) throws ApiException {
        //default asc order
        boolean isDescOrder = "Desc".equalsIgnoreCase(order);

        String dbOrderBy = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, orderBy);
        if (Stream.of(TableField.ARTICLE_FIELD).noneMatch(dbOrderBy::equals)) {
            throw new ApiException(400, "pagination error, check your orderBy(lowerCamel) param");
        }

        Wrapper<Article> wrapper = new QueryWrapper<Article>()
                .select(TableField.ARTICLE_FIELD_WITHOUT_BLOB)
                .eq("article_site_id", sid)
                .orderByDesc(isDescOrder, dbOrderBy)
                .orderByAsc(!isDescOrder, dbOrderBy);

        return articleMapper.selectPage(new Page<>(pageNumber, rowsPerPage), wrapper);
    }

    @Override
    @CacheEvict(value = {"article", "articleList"}, allEntries = true)
    public Integer createArticle(ArticleUploadRequest request) throws ApiException {
        Article article = request.getArticle();
        List<String> fileNames = request.getFileNames();
        if (isInvalid(article)) {
            throw new ApiException(400, "some article field not complete");
        }
        if (articleMapper.insert(article) == 0) {
            throw new ApiException(500, "insert article failed");
        }

        if (article.getArticleCreateTime() == null) {
            article.setArticleCreateTime(LocalDateTime.now());
        }

        if (fileNames != null) {
            fileNames.forEach(name -> saveFile(article, name));
        }

        return article.getArticleId();
    }

    @Override
    @CacheEvict(value = {"article", "articleList"}, allEntries = true)
    public Article updateArticle(ArticleUploadRequest request) throws ApiException {
        Article article = request.getArticle();
        if (article.getArticleId() == null || article.getArticleId() <= 0) {
            throw new ApiException(400, "must specify articleId field");
        }

        if (!articleMapper.isExist(article.getArticleId())) {
            throw new ApiException(400, "article is not exists");
        }

        article.setArticleUpdateTime(LocalDateTime.now());

        if (articleMapper.updateById(article) == 0) {
            throw new ApiException(500, "update article failed");
        }

        if (request.getFileNames() != null) {
            request.getFileNames().forEach(name -> saveFile(article, name));
        }

        return article;
    }

    private void saveFile(Article article, String name) {
        File file = new File();
        file.setFileArticleId(article.getArticleId());
        file.setFileKey(name);
        file.setFileSiteId(article.getArticleSiteId());
        fileMapper.insert(file);
    }

    @Override
    @CacheEvict(value = {"article", "articleList"}, allEntries = true)
    public Integer deleteArticle(Integer id) throws ApiException {
        int count = articleMapper.deleteById(id);
        if (count != 1) {
            throw new ApiException(400, "delete failed, article not exist");
        }
        return count;
    }

    @Override
    public Integer countArticle(Integer sid) {
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>().eq("articleSiteId", sid);
        return articleMapper.selectCount(wrapper);
    }

    /**
     * 判断文章是否非法
     */
    private boolean isInvalid(Article article) {
        return Strings.isNullOrEmpty(article.getArticleAuthor()) ||
                Strings.isNullOrEmpty(article.getArticleTitle()) ||
                Strings.isNullOrEmpty(article.getArticleType()) ||
                article.getArticleSiteId() == null ||
                article.getArticleCategoryId() == null;
    }
}
