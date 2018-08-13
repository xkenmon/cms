package com.xkenmon.cms.admin.api;

import com.qiniu.common.Zone;
import com.qiniu.common.ZoneReqInfo;
import com.qiniu.util.Auth;
import com.xkenmon.cms.admin.auth.CurrentUser;
import com.xkenmon.cms.admin.auth.UserPrincipal;
import com.xkenmon.cms.admin.config.QiniuConfig;
import com.xkenmon.cms.admin.dto.ApiMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bigmeng
 * @date 2018/8/8
 */
@RestController
@RequestMapping("/qiniu/")
@Api(value = "qiniu", description = "七牛api地址")
public class QiniuApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(QiniuApi.class);

    private final
    QiniuConfig qiniuConfig;

    @Autowired
    public QiniuApi(QiniuConfig qiniuConfig) {
        this.qiniuConfig = qiniuConfig;
    }

    @ApiOperation("获取七牛上传token")
    @GetMapping("upToken")
    public ApiMessage getUpToken(@CurrentUser UserPrincipal user) {

        String token = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey()).uploadToken(qiniuConfig.getBucket());
        LOGGER.info("user [{}] request qiniu upload token - {}", user.getUsername(), token);
        return ApiMessage.success(token);
    }

    @ApiOperation("获取七牛上传HTTP地址")
    @GetMapping("upHttpAddr")
    public ApiMessage getupAddr(@CurrentUser UserPrincipal user) {
        String addr = Zone.autoZone().getUpHttp(new ZoneReqInfo(qiniuConfig.getAccessKey(), qiniuConfig.getBucket()));
        LOGGER.info("user [{}] request qiniu http upload addr - {}", user.getUsername(), addr);
        return ApiMessage.success(addr);
    }

    @ApiOperation("获取七牛上传HTTPS地址")
    @GetMapping("upHttpsAddr")
    public ApiMessage getUpHttpsAddr(@CurrentUser UserPrincipal user) {
        String addr = Zone.autoZone().getUpHttps(new ZoneReqInfo(qiniuConfig.getAccessKey(), qiniuConfig.getBucket()));
        LOGGER.info("user [{}] request qiniu https upload addr - {}", user.getUsername(), addr);
        return ApiMessage.success(addr);
    }

    @ApiOperation("获取CDN域名")
    @GetMapping("cdnDomain")
    public ApiMessage getCdnDomain(@CurrentUser UserPrincipal user) {
        String domain = qiniuConfig.getCdnDomain();
        LOGGER.info("user [{}] request cdn domain - {}", user.getUsername(), domain);
        return ApiMessage.success(domain);
    }
}
