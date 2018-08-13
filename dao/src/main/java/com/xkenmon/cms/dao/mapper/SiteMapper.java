package com.xkenmon.cms.dao.mapper;

import com.xkenmon.cms.dao.entity.Site;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author AutoGenerator
 * @since 2018-08-06
 */
public interface SiteMapper extends BaseMapper<Site> {

    @Select("select * from cms_site where site_url = #{url} limit 1")
    public Site selectByUrl(String url);
}
