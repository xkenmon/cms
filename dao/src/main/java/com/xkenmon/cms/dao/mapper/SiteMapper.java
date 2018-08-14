package com.xkenmon.cms.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xkenmon.cms.dao.entity.Site;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author AutoGenerator
 * @since 2018-08-06
 */
@Repository
public interface SiteMapper extends BaseMapper<Site> {

    /**
     * 通过url选择站点
     * @param url 站点URL
     * @return 站点
     */
    @Select("select * from cms_site where site_url = #{url} limit 1")
    Site selectByUrl(String url);
}
