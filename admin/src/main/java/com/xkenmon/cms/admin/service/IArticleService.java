package com.xkenmon.cms.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xkenmon.cms.admin.dto.ArticleUploadRequest;
import com.xkenmon.cms.admin.exception.ApiException;
import com.xkenmon.cms.dao.entity.Article;

/**
 * @author bigmeng
 */
public interface IArticleService {
    /**
     * 根据指定ID返回整个文章对象
     *
     * @param id 文章ID
     * @return id对应的文章
     * @throws ApiException 交由{@link com.xkenmon.cms.admin.api.ControllerExceptionHandler} 处理的http错误信息
     */
    Article selectById(Integer id) throws ApiException;

    /**
     * 根据提供的分页信息查询文章
     *
     * @param rowsPerPage 每页条目数量
     * @param pageNumber  页码
     * @param orderBy     排序依据,小驼峰，如articleTitle
     * @param order       排序策略，asc|desc
     * @param sid         站点ID
     * @return 分页信息对应的文章列表
     * @throws ApiException 交由{@link com.xkenmon.cms.admin.api.ControllerExceptionHandler} 处理的http错误信息
     */
    IPage<Article> queryArticleList(Integer rowsPerPage, Integer pageNumber, String orderBy, String order, Integer sid) throws ApiException;


    /**
     * 添加文章
     *
     * @param article 需要被添加的文章对象
     * @return 文章ID
     * @throws ApiException 交由{@link com.xkenmon.cms.admin.api.ControllerExceptionHandler} 处理的http错误信息
     */
    Integer createArticle(ArticleUploadRequest article) throws ApiException;

    /**
     * 根据传来文章的ID更新文章
     *
     * @param article 需要更新的文章，必须指定articleId字段
     * @return 更新成功的文章
     * @throws ApiException 交由{@link com.xkenmon.cms.admin.api.ControllerExceptionHandler} 处理的http错误信息
     */
    Article updateArticle(Article article) throws ApiException;

    /**
     * 删除文章
     *
     * @param id 被删除文章的ID
     * @return 删除的数量，成功为1
     * @throws ApiException 交由{@link com.xkenmon.cms.admin.api.ControllerExceptionHandler} 处理的http错误信息
     */
    Integer deleteArticle(Integer id) throws ApiException;

    /**
     * 统计文章数量
     * @param sid 指定站点id
     * @return 文章数量
     */
    Integer countArticle(Integer sid);
}
