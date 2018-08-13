package com.xkenmon.cms.dao.mapper;

import com.xkenmon.cms.dao.entity.File;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author AutoGenerator
 * @since 2018-08-06
 */
public interface FileMapper extends BaseMapper<File> {

    /**
     * 根据文章ID选择文件key
     *
     * @param articleId 文章ID
     * @return 文件 key List
     */
    @Select("select file_key from cms_file where file_article_id = #{articleId}")
    List<String> selectFileKeyByArticleId(Integer articleId);
}
