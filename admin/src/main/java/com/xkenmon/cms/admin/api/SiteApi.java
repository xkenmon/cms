package com.xkenmon.cms.admin.api;

import com.xkenmon.cms.admin.annotation.Auth;
import com.xkenmon.cms.admin.constant.ModuleNames;
import com.xkenmon.cms.admin.service.ISiteService;
import com.xkenmon.cms.dao.entity.Site;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bigmeng
 * @date 2018/8/14
 */
@Api(value = "site", description = "站点操作接口")
@RestController
@RequestMapping("/site")
public class SiteApi {
    private final
    ISiteService siteService;

    @Autowired
    public SiteApi(ISiteService siteService) {
        this.siteService = siteService;
    }

    @ApiOperation("通过站点id获取站点")
    @GetMapping("{siteId}")
    @Auth(siteId = "#siteId", modules = ModuleNames.CONTENT_MANAGE)
    public Site getById(@PathVariable("siteId") Integer siteId) {
        return siteService.getById(siteId);
    }
}
