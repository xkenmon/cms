package com.xkenmon.cms.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.xkenmon.cms.common.dto.Tree;
import com.xkenmon.cms.admin.exception.ApiException;
import com.xkenmon.cms.admin.service.ICategoryService;
import com.xkenmon.cms.common.constant.TableField;
import com.xkenmon.cms.dao.entity.Category;
import com.xkenmon.cms.dao.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bigmeng
 * @date 2018/8/8
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    @Cacheable("category")
    public Category selectById(Integer id) throws ApiException {
        Category category = categoryMapper.selectById(id);
        if (null == category) {
            throw new ApiException(404, "category not found");
        }
        return category;
    }

    @Override
    @Cacheable("categoryList")
    public IPage<Category> queryCategoryList(Integer rowsPerPage, Integer pageNumber, String orderBy, String order, Integer siteId) throws ApiException {
        boolean isAscOrder = "asc".equalsIgnoreCase(order);
        String dbOrderBy = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, orderBy);
        if (!TableField.CATEGORY_FIELD.contains(dbOrderBy)) {
            throw new ApiException(400, "pagination error, check your orderBy(lowerCamel) param");
        }
        Wrapper<Category> wrapper = new QueryWrapper<Category>()
                .orderBy(true, isAscOrder, dbOrderBy);

        return categoryMapper.selectPage(new Page<>(pageNumber, rowsPerPage), wrapper);
    }

    @Override
    @CacheEvict(value = {"category", "categoryList", "categoryTree"}, allEntries = true)
    public Integer createCategory(Category category) throws ApiException {
        if (isInvalid(category)) {
            throw new ApiException(400, "some category field not complete");
        }

        if (categoryMapper.insert(category) == 0) {
            throw new ApiException(500, "insert category failed");
        }

        return category.getCategoryId();
    }

    @Override
    @CacheEvict(value = {"categoryTree", "categoryList"}, allEntries = true)
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

        return category;
    }

    @Override
    @CacheEvict(value = {"categoryList", "category", "categoryTree"}, allEntries = true)
    public Integer deleteCategory(Integer id) throws ApiException {
        int count = categoryMapper.deleteById(id);
        if (count != 1) {
            throw new ApiException(400, "delete failed, category not exist");
        }
        return count;
    }

    @Override
    @Cacheable("categoryTree")
    public List<Tree<Category>> getCategoryTree(Integer sid) throws ApiException {
        QueryWrapper<Category> wrapper = new QueryWrapper<Category>()
                .eq("category_site_id", sid)
                .eq("category_level", -1);
        Category root = categoryMapper.selectOne(wrapper);
        if (root == null) {
            throw new ApiException(500, "数据错误，找不到栏目根节点(id=1,title=root)");
        }
        return ImmutableList.of(getTree(root, sid));
    }

    @Override
    public Integer countCategory(Integer id) {
        QueryWrapper<Category> wrapper = new QueryWrapper<Category>().eq("categorySiteId",id);
        return categoryMapper.selectCount(wrapper);
    }

    private Tree<Category> getTree(Category category, Integer sid) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<Category>()
                .eq("category_parent_id", category.getCategoryId())
                .eq("category_site_id", sid);

        List<Category> childList = categoryMapper.selectList(queryWrapper);
        Tree<Category> tree = new Tree<>();
        tree.setData(category);
        if (childList == null || childList.isEmpty()) {
            return tree;
        }
        tree.setChild(childList.stream().map(c -> getTree(c, sid)).collect(Collectors.toList()));
        return tree;
    }

    private boolean isInvalid(Category category) {
        return Strings.isNullOrEmpty(category.getCategoryTitle()) ||
                Strings.isNullOrEmpty(category.getCategoryType()) ||
                category.getCategoryId() == null ||
                category.getCategorySiteId() <= 0;
    }
}
