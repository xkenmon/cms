package com.xkenmon.cms.admin.service;

import com.xkenmon.cms.dao.entity.Site;

import java.util.List;

/**
 * @author bigmeng
 * @date 2018/8/10
 */
public interface ISiteService {
    /**
     * 通过id选择站点
     *
     * @param id 站点id
     * @return 站点
     */
    Site getById(Integer id);

    List<Site> getAuthedSite(Integer uid);
}
