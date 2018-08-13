package com.xkenmon.cms.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xkenmon.cms.dao.entity.Article;
import com.xkenmon.cms.dao.entity.Category;
import com.xkenmon.cms.dao.entity.Site;
import com.xkenmon.cms.dao.mapper.ArticleMapper;
import com.xkenmon.cms.dao.mapper.CategoryMapper;
import com.xkenmon.cms.dao.mapper.SiteMapper;
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
public class CategoryWebService {
    private final Logger logger = LoggerFactory.getLogger(CategoryWebService.class);

    private final
    SiteMapper siteMapper;

    private final
    ArticleMapper articleMapper;

    private final
    CategoryMapper categoryMapper;

    public CategoryWebService(SiteMapper siteMapper, ArticleMapper articleMapper, CategoryMapper categoryMapper) {
        this.siteMapper = siteMapper;
        this.articleMapper = articleMapper;
        this.categoryMapper = categoryMapper;
    }

    @Cacheable(value = "categoryModel", key = "#id",sync = true)
    public ModelResult getCategoryModel(Integer id) {
        ModelResult result = new ModelResult();

        Category category = categoryMapper.selectById(id);


        //---------------------------------------default properties start----------------------------------------------//


        Site site = siteMapper.selectById(category.getCategorySiteId());

        //Get 0 level categorise in this site (displayed above the homepage of the website)
//        CategoryExample categoryExample = new CategoryExample();
//        categoryExample.or().andCategorySiteIdEqualTo(category.getCategorySiteId()).andCategoryLevelEqualTo(0);
//        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
//        //Each level 0 category into category tree
//        List<CategoryTree> categoryTreeList = categoryList.stream().map(this::toTree).collect(Collectors.toList());

        // Get this category's article list (without BLOBs)
        QueryWrapper<Article> queryWrapper = new QueryWrapper<Article>().eq("article_category_id", category.getCategoryId());
        List<Article> articleList = articleMapper.selectList(queryWrapper);

        result.put("articleList", articleList)
                .put("category", category)
                .put("site", site);
        return result;
    }

    public void addCategoryHit(Category category) {
        Integer hit = category.getCategoryHit();
        if (hit == null) {
            category.setCategoryHit(0);
            hit = 0;
        }
        category.setCategoryHit(hit + 1);
        categoryMapper.updateById(category);
    }

//    CategoryTree toTree(Category category) {
//        CategoryTree tree = new CategoryTree();
//        int columnId = category.getCategoryId();
//        tree.setId(columnId);
//        tree.setName(category.getCategoryTitle());
//        tree.setSpread(false);
//        CategoryExample categoryExample = new CategoryExample();
//        categoryExample.or().andCategoryParentIdEqualTo(category.getCategoryId()).andCategoryStatusEqualTo(1);
//        //子栏目
//        List<Category> list = categoryMapper.selectByExample(categoryExample);
//        if (list == null || list.isEmpty()) {
//            tree.setChildren(null);
//            return tree;
//        }

//        tree.setChildren(list.stream().map(this::toTree).collect(Collectors.toList()));
//
//        return tree;
//    }
}
