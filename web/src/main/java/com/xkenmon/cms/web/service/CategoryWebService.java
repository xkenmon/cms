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
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${qiniu.cdnDomain}")
    private String cdnDomain;

    public CategoryWebService(SiteMapper siteMapper, ArticleMapper articleMapper, CategoryMapper categoryMapper) {
        this.siteMapper = siteMapper;
        this.articleMapper = articleMapper;
        this.categoryMapper = categoryMapper;
    }

    @Cacheable(value = "categoryModel", key = "#id", sync = true)
    public ModelResult getCategoryModel(Integer id) {
        ModelResult result = new ModelResult();

        Category category = categoryMapper.selectById(id);


        //---------------------------------------default properties start----------------------------------------------//


        Site site = siteMapper.selectById(category.getCategorySiteId());

        QueryWrapper<Article> queryWrapper = new QueryWrapper<Article>().eq("article_category_id", category.getCategoryId());
        List<Article> articleList = articleMapper.selectList(queryWrapper);

        result.put("articleList", articleList)
                .put("category", category)
                .put("site", site)
                .put("cdnDomain", cdnDomain)
                .put("baseSkinPath", category.getCategorySkin().split("/")[0]);
        return result;
    }

    public void addCategoryHit(Category category) {
        Category update = new Category();
        update.setCategoryId(category.getCategoryId());
        update.setCategoryHit(category.getCategoryHit() == null ? 1 : category.getCategoryHit() + 1);
        categoryMapper.updateById(update);
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
