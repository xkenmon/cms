package com.xkenmon.cms.dao.mapper;

import com.xkenmon.cms.dao.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author AutoGenerator
 * @since 2018-08-06
 */
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 判断文章是否存在
     *
     * @param id 文章ID
     * @return 是否存在
     */
    @Select("select exists(select article_id from cms_article where article_id = #{id})")
    Boolean isExist(Integer id);
}
