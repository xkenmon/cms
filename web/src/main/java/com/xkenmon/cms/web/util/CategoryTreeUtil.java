package com.xkenmon.cms.web.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xkenmon.cms.common.constant.AccessStatus;
import com.xkenmon.cms.common.constant.CMSContentType;
import com.xkenmon.cms.common.dto.Tree;
import com.xkenmon.cms.dao.entity.Category;
import com.xkenmon.cms.dao.mapper.CategoryMapper;
import com.xkenmon.cms.web.ApplicationContextProvider;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bigmeng
 * @date 2018/8/14
 */
public class CategoryTreeUtil {
    private static CategoryMapper mapper = ApplicationContextProvider.getBean(CategoryMapper.class);

    public static Tree<Category> query(Integer sid) {
        QueryWrapper<Category> wrapper = new QueryWrapper<Category>()
                .eq("category_site_id", sid)
                .eq("category_level", -1);
        Category root = mapper.selectOne(wrapper);
        return getTree(root, sid);
    }

    private static Tree<Category> getTree(Category category, Integer sid) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<Category>()
                .eq("category_parent_id", category.getCategoryId())
                .eq("category_site_id", sid)
                .eq("category_status", AccessStatus.ACCESS);

        List<Category> childList = mapper.selectList(queryWrapper);
        Tree<Category> tree = new Tree<>();
        tree.setData(category);
        if (childList == null || childList.isEmpty()) {
            return tree;
        }
        tree.setChild(childList.stream().map(c -> getTree(c, sid)).collect(Collectors.toList()));
        return tree;
    }
}
