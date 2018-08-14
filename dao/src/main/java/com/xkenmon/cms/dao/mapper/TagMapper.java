package com.xkenmon.cms.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xkenmon.cms.dao.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author AutoGenerator
 * @since 2018-08-06
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 选取指定文章的所有标签
     *
     * @param articleId 文章ID
     * @return 该文章对应的Tag列表
     */
    List<Tag> selectTagsByArticleId(Integer articleId);
}
