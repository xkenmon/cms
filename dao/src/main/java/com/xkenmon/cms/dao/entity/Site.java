package com.xkenmon.cms.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author AutoGenerator
 * @since 2018-08-06
 */
@TableName("cms_site")
public class Site implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "site_id", type = IdType.AUTO)
    private Integer siteId;

    private String siteName;

    private String siteUrl;

    private String siteDesc;

    private String siteCopyright;

    private String siteSkin;

    private LocalDateTime siteCreateTime;

    /**
     * 0代表待审核，1代表通过，2代表审核未通过
     */
    private Integer siteStatus;

    private String sitePic;

    private Integer siteHit;


    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getSiteDesc() {
        return siteDesc;
    }

    public void setSiteDesc(String siteDesc) {
        this.siteDesc = siteDesc;
    }

    public String getSiteCopyright() {
        return siteCopyright;
    }

    public void setSiteCopyright(String siteCopyright) {
        this.siteCopyright = siteCopyright;
    }

    public String getSiteSkin() {
        return siteSkin;
    }

    public void setSiteSkin(String siteSkin) {
        this.siteSkin = siteSkin;
    }

    public LocalDateTime getSiteCreateTime() {
        return siteCreateTime;
    }

    public void setSiteCreateTime(LocalDateTime siteCreateTime) {
        this.siteCreateTime = siteCreateTime;
    }

    public Integer getSiteStatus() {
        return siteStatus;
    }

    public void setSiteStatus(Integer siteStatus) {
        this.siteStatus = siteStatus;
    }

    public String getSitePic() {
        return sitePic;
    }

    public void setSitePic(String sitePic) {
        this.sitePic = sitePic;
    }

    public Integer getSiteHit() {
        return siteHit;
    }

    public void setSiteHit(Integer siteHit) {
        this.siteHit = siteHit;
    }

    @Override
    public String toString() {
        return "Site{" +
        ", siteId=" + siteId +
        ", siteName=" + siteName +
        ", siteUrl=" + siteUrl +
        ", siteDesc=" + siteDesc +
        ", siteCopyright=" + siteCopyright +
        ", siteSkin=" + siteSkin +
        ", siteCreateTime=" + siteCreateTime +
        ", siteStatus=" + siteStatus +
        ", sitePic=" + sitePic +
        ", siteHit=" + siteHit +
        "}";
    }
}
