package com.xkenmon.cms.admin.dto;

import com.xkenmon.cms.dao.entity.Article;

import java.util.List;

/**
 * @author bigmeng
 * @date 2018/8/10
 */
public class ArticleUploadRequest {
    private Article article;
    private List<String> fileNames;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }
}
