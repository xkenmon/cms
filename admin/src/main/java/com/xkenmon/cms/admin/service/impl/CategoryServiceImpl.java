package com.xkenmon.cms.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.xkenmon.cms.admin.dto.Tree;
import com.xkenmon.cms.admin.exception.ApiException;
import com.xkenmon.cms.admin.service.ICategoryService;
import com.xkenmon.cms.dao.entity.Category;
import com.xkenmon.cms.dao.mapper.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bigmeng
 * @date 2018/8/8
 */
@Service
public class CategoryServiceImpl implements ICategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private static final List<String> DB_FILED = ImmutableList.of("category_id", "category_title", "category_create_time",
            "category_update_time", "category_parent_id", "category_level", "category_site_id", "category_status",
            "category_desc", "category_order", "category_skin", "category_type", "category_in_homepage", "category_hit",
            "category_id", "category_title", "category_create_time", "category_update_time", "category_parent_id",
            "category_level", "category_site_id", "category_status", "category_desc", "category_order", "category_skin",
            "category_type", "category_in_homepage", "category_hit");
//            .stream()
//            .map(e -> CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, e))
//            .collect(Collectors.toList());

    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Category selectById(Integer id) throws ApiException {
        Category category = categoryMapper.selectById(id);
        if (null == category) {
            throw new ApiException(404, "category not found");
        }
        LOGGER.info("query category - id: {}, title: {}", category.getCategoryId(), category.getCategoryTitle());
        return category;
    }

    @Override
    public IPage<Category> queryCategoryList(Integer rowsPerPage, Integer pageNumber, String orderBy, String order) throws ApiException {
        boolean isAscOrder = "asc".equalsIgnoreCase(order);
        String dbOrderBy = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, orderBy);
        if (!DB_FILED.contains(dbOrderBy)) {
            throw new ApiException(400, "pagination error, check your orderBy(lowerCamel) param");
        }
        Wrapper<Category> wrapper = new QueryWrapper<Category>()
                .orderBy(true, isAscOrder, dbOrderBy);

        LOGGER.info(
                "query category list - pageNumber: {}, rowsPerPage: {}, orderBy: {}, order: {}",
                pageNumber, rowsPerPage, orderBy, order
        );

        return categoryMapper.selectPage(new Page<>(pageNumber, rowsPerPage), wrapper);
    }

    @Override
    public Integer createCategory(Category category) throws ApiException {
        if (isInvalid(category)) {
            throw new ApiException(400, "some category field not complete");
        }

        if (categoryMapper.insert(category) == 0) {
            throw new ApiException(500, "insert category failed");
        }

        LOGGER.info("insert category - id: {}, title: {}", category.getCategoryId(), category.getCategoryTitle());
        return category.getCategoryId();
    }

    @Override
    public Category updateCategory(Category category) throws ApiException {
        if (category.getCategoryId() == null || category.getCategoryId() <= 0) {
            throw new ApiException(400, "must specify category field");
        }
        if (isInvalid(category)) {
            throw new ApiException(400, "some category field not complete");
        }

        if (categoryMapper.updateById(category) == 0) {
            throw new ApiException(500, "update category failed");
        }

        LOGGER.info("update category - id: {}, title: {}", category.getCategoryId(), category.getCategoryTitle());
        return category;
    }

    @Override
    public Integer deleteCategory(Integer id) throws ApiException {
        int count = categoryMapper.deleteById(id);
        if (count != 1) {
            throw new ApiException(400, "delete failed, category not exist");
        }
        LOGGER.info("delete category - id: {}", id);
        return count;
    }

    @Override
    public List<Tree<Category>> getCategoryTree() throws ApiException {
        Category root = categoryMapper.selectById(1);
        if (root == null) {
            throw new ApiException(500, "数据错误，找不到栏目根节点(id=1,title=root)");
        }
        return ImmutableList.of(getTree(root));
    }

    private Tree<Category> getTree(Category category) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<Category>().eq("category_parent_id", category.getCategoryId());
        List<Category> childList = categoryMapper.selectList(queryWrapper);
        Tree<Category> tree = new Tree<>();
        tree.setData(category);
        if (childList == null || childList.isEmpty()) {
            return tree;
        }
        tree.setChild(childList.stream().map(this::getTree).collect(Collectors.toList()));
        return tree;
    }

    private boolean isInvalid(Category category) {
        return Strings.isNullOrEmpty(category.getCategoryTitle()) ||
                Strings.isNullOrEmpty(category.getCategoryType()) ||
                category.getCategoryId() == null ||
                category.getCategorySiteId() <= 0;
    }
}
