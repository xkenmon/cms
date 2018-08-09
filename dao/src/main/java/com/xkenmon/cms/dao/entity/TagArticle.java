package com.xkenmon.cms.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author AutoGenerator
 * @since 2018-08-06
 */
@TableName("cms_tag_article")
public class TagArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer tagId;

    private Integer articleId;


    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "TagArticle{" +
        ", tagId=" + tagId +
        ", articleId=" + articleId +
        "}";
    }
}
