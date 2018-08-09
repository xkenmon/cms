package com.xkenmon.cms.admin.api;

import com.qiniu.common.Zone;
import com.qiniu.common.ZoneReqInfo;
import com.qiniu.util.Auth;
import com.xkenmon.cms.admin.config.QiniuConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    private final
    QiniuConfig qiniuConfig;

    @Autowired
    public QiniuApi(QiniuConfig qiniuConfig) {
        this.qiniuConfig = qiniuConfig;
    }

    @ApiOperation("获取七牛上传token")
    @GetMapping("upToken")
    public String getUpToken() {
        return Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey()).uploadToken(qiniuConfig.getBucket());
    }

    @ApiOperation("获取七牛上传HTTP地址")
    @GetMapping("upHttpAddr")
    public String getupAddr() {
        return Zone.autoZone().getUpHttp(new ZoneReqInfo(qiniuConfig.getAccessKey(), qiniuConfig.getBucket()));
    }

    @ApiOperation("获取七牛上传HTTPS地址")
    @GetMapping("upHttpsAddr")
    public String getUpHttpsAddr() {
        return Zone.autoZone().getUpHttps(new ZoneReqInfo(qiniuConfig.getAccessKey(), qiniuConfig.getBucket()));
    }

    @ApiOperation("获取CDN域名")
    @GetMapping("cdnDomain")
    public String getCdnDomain() {
        return qiniuConfig.getCdnDomain();
    }
}
