package com.xkenmon.cms.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xkenmon.cms.common.dto.Tree;
import com.xkenmon.cms.admin.exception.ApiException;
import com.xkenmon.cms.dao.entity.Category;

import java.util.List;

/**
 * @author bigmeng
 * @date 2018/8/8
 */
public interface ICategoryService {
    /**
     * 根据ID返回栏目对象
     *
     * @param id 栏目ID
     * @return id对应的栏目
     * @throws ApiException 交由{@link com.xkenmon.cms.admin.api.ControllerExceptionHandler} 处理的http错误信息
     */
    Category selectById(Integer id) throws ApiException;

    /**
     * 根据提供的分页信息
     *
     * @param rowsPerPage 每页条目数量
     * @param pageNumber  页码数
     * @param orderBy     排序依据，小驼峰命名
     * @param order       排序策略，asc|desc
     * @param siteId      站点ID
     * @return 栏目列表
     * @throws ApiException 交由{@link com.xkenmon.cms.admin.api.ControllerExceptionHandler} 处理的http错误信息
     */
    IPage<Category> queryCategoryList(Integer rowsPerPage, Integer pageNumber, String orderBy, String order, Integer siteId) throws ApiException;

    /**
     * 添加栏目
     *
     * @param category 需要添加的栏目对象
     * @return 添加成功的栏目ID
     * @throws ApiException 交由{@link com.xkenmon.cms.admin.api.ControllerExceptionHandler} 处理的http错误信息
     */
    Integer createCategory(Category category) throws ApiException;

    /**
     * 根据栏目ID更新栏目
     *
     * @param category 需要更新的栏目，需指定栏目ID字段
     * @return 更新成功的栏目
     * @throws ApiException 交由{@link com.xkenmon.cms.admin.api.ControllerExceptionHandler} 处理的http错误信息
     */
    Category updateCategory(Category category) throws ApiException;

    /**
     * 删除栏目
     *
     * @param id 被删除的栏目ID
     * @return 删除的数量，成功为1
     * @throws ApiException 交由{@link com.xkenmon.cms.admin.api.ControllerExceptionHandler} 处理的http错误信息
     */
    Integer deleteCategory(Integer id) throws ApiException;

    /**
     * 列出栏目树
     * @param sid 站点id
     * @return 栏目树
     * @throws ApiException 交由{@link com.xkenmon.cms.admin.api.ControllerExceptionHandler} 处理的http错误信息
     */
    List<Tree<Category>> getCategoryTree(Integer sid) throws ApiException;
}
