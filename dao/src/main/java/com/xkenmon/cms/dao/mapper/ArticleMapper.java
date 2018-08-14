package com.xkenmon.cms.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xkenmon.cms.dao.entity.Article;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author AutoGenerator
 * @since 2018-08-06
 */
@Repository
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
