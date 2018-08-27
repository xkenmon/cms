package com.xkenmon.cms.admin.service.impl;

import com.xkenmon.cms.admin.service.ISiteService;
import com.xkenmon.cms.dao.entity.Site;
import com.xkenmon.cms.dao.mapper.PermissionMapper;
import com.xkenmon.cms.dao.mapper.SiteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author bigmeng
 * @date 2018/8/12
 */
@Service
public class SiteServiceImpl implements ISiteService {

    private final
    SiteMapper siteMapper;

    private final
    PermissionMapper permissionMapper;

    @Autowired
    public SiteServiceImpl(SiteMapper siteMapper, PermissionMapper permissionMapper) {
        this.siteMapper = siteMapper;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public Site getById(Integer id) {
        return siteMapper.selectById(id);
    }

    @Override
    public List<Site> getAuthedSite(Integer uid) {
        return permissionMapper.getPermittedSite(uid);
    }
}
