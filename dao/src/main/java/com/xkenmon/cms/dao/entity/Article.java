package com.xkenmon.cms.dao.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author AutoGenerator
 * @since 2018-08-06
 */
@TableName("cms_article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "article_id", type = IdType.AUTO)
    private Integer articleId;

    private String articleTitle;

    private String articleType;

    private String articleAuthor;

    private String articleUrl;

    private Integer articleOrder;

    private Integer articleSiteId;

    private Integer articleCategoryId;

    private LocalDateTime articleCreateTime;

    private LocalDateTime articleUpdateTime;

    private String articleThumb;

    private Integer articleHit;

    private String articleDesc;

    /**
     * 0代表待审核，1代表审核通过，2代表审核未通过
     */
    private Integer articleStatus;

    private String articleContent;

    private String articleSkin;

    private Boolean articleInHomepage;

    private LocalDateTime articleReleaseTime;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getArticleAuthor() {
        return articleAuthor;
    }

    public void setArticleAuthor(String articleAuthor) {
        this.articleAuthor = articleAuthor;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public Integer getArticleOrder() {
        return articleOrder;
    }

    public void setArticleOrder(Integer articleOrder) {
        this.articleOrder = articleOrder;
    }

    public Integer getArticleSiteId() {
        return articleSiteId;
    }

    public void setArticleSiteId(Integer articleSiteId) {
        this.articleSiteId = articleSiteId;
    }

    public Integer getArticleCategoryId() {
        return articleCategoryId;
    }

    public void setArticleCategoryId(Integer articleCategoryId) {
        this.articleCategoryId = articleCategoryId;
    }

    public LocalDateTime getArticleCreateTime() {
        return articleCreateTime;
    }

    public void setArticleCreateTime(LocalDateTime articleCreateTime) {
        this.articleCreateTime = articleCreateTime;
    }

    public LocalDateTime getArticleUpdateTime() {
        return articleUpdateTime;
    }

    public void setArticleUpdateTime(LocalDateTime articleUpdateTime) {
        this.articleUpdateTime = articleUpdateTime;
    }

    public String getArticleThumb() {
        return articleThumb;
    }

    public void setArticleThumb(String articleThumb) {
        this.articleThumb = articleThumb;
    }

    public Integer getArticleHit() {
        return articleHit;
    }

    public void setArticleHit(Integer articleHit) {
        this.articleHit = articleHit;
    }

    public String getArticleDesc() {
        return articleDesc;
    }

    public void setArticleDesc(String articleDesc) {
        this.articleDesc = articleDesc;
    }

    public Integer getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(Integer articleStatus) {
        this.articleStatus = articleStatus;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleSkin() {
        return articleSkin;
    }

    public void setArticleSkin(String articleSkin) {
        this.articleSkin = articleSkin;
    }

    public Boolean getArticleInHomepage() {
        return articleInHomepage;
    }

    public void setArticleInHomepage(Boolean articleInHomepage) {
        this.articleInHomepage = articleInHomepage;
    }

    public LocalDateTime getArticleReleaseTime() {
        return articleReleaseTime;
    }

    public void setArticleReleaseTime(LocalDateTime articleReleaseTime) {
        this.articleReleaseTime = articleReleaseTime;
    }

    @Override
    public String toString() {
        return "Article{" +
                ", articleId=" + articleId +
                ", articleTitle=" + articleTitle +
                ", articleType=" + articleType +
                ", articleAuthor=" + articleAuthor +
                ", articleUrl=" + articleUrl +
                ", articleOrder=" + articleOrder +
                ", articleSiteId=" + articleSiteId +
                ", articleCategoryId=" + articleCategoryId +
                ", articleCreateTime=" + articleCreateTime +
                ", articleUpdateTime=" + articleUpdateTime +
                ", articleThumb=" + articleThumb +
                ", articleHit=" + articleHit +
                ", articleDesc=" + articleDesc +
                ", articleStatus=" + articleStatus +
                ", articleContent=" + articleContent +
                ", articleSkin=" + articleSkin +
                ", articleInHomepage=" + articleInHomepage +
                ", articleReleaseTime=" + articleReleaseTime +
                "}";
    }
}
