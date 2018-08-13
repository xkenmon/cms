package com.xkenmon.cms.common.constant;

import com.google.common.collect.ImmutableList;


/**
 * @author bigmeng
 * @date 2018/8/12
 */
public class TableField {
    public static final String[] ARTICLE_FIELD = new String[]{
            "article_id",
            "article_title",
            "article_type",
            "article_author",
            "article_url",
            "article_order",
            "article_site_id",
            "article_category_id",
            "article_create_time",
            "article_update_time",
            "article_thumb",
            "article_hit",
            "article_desc",
            "article_status",
            "article_content",
            "article_skin",
            "article_in_homepage",
            "article_release_time"
    };

    public static final String[] ARTICLE_FIELD_WITHOUT_BLOB = new String[]{
            "article_id",
            "article_title",
            "article_type",
            "article_author",
            "article_url",
            "article_order",
            "article_site_id",
            "article_category_id",
            "article_create_time",
            "article_update_time",
            "article_thumb",
            "article_hit",
            "article_desc",
            "article_status",
            "article_skin",
            "article_in_homepage",
            "article_release_time"
    };

    public static final ImmutableList<String> CATEGORY_FIELD = ImmutableList.of("category_id", "category_title", "category_create_time",
            "category_update_time", "category_parent_id", "category_level", "category_site_id", "category_status",
            "category_desc", "category_order", "category_skin", "category_type", "category_in_homepage", "category_hit",
            "category_id", "category_title", "category_create_time", "category_update_time", "category_parent_id",
            "category_level", "category_site_id", "category_status", "category_desc", "category_order", "category_skin",
            "category_type", "category_in_homepage", "category_hit");
}
