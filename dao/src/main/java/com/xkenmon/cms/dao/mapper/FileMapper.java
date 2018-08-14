package com.xkenmon.cms.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xkenmon.cms.dao.entity.File;
import org.apache.ibatis.annotations.Select;
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
