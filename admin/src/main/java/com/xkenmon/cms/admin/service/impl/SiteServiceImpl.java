package com.xkenmon.cms.admin.service.impl;

import com.xkenmon.cms.admin.service.ISiteService;
import com.xkenmon.cms.dao.entity.Site;
import com.xkenmon.cms.dao.mapper.SiteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bigmeng
 * @date 2018/8/12
 */
@Service
public class SiteServiceImpl implements ISiteService {

    private final
    SiteMapper siteMapper;

    @Autowired
    public SiteServiceImpl(SiteMapper siteMapper) {
        this.siteMapper = siteMapper;
    }

    @Override
    public Site getById(Integer id) {
        return siteMapper.selectById(id);
    }
}
