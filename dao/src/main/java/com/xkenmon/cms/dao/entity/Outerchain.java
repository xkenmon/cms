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
@TableName("cms_outerchain")
public class Outerchain implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "outerchain_id", type = IdType.AUTO)
    private Integer outerchainId;

    private String outerchainUrl;

    private LocalDateTime outerchainCreateTime;

    /**
     * 外链的描述
     */
    private String outerchainDesc;

    private Integer outerchainSiteId;

    private String outerchainName;

    private LocalDateTime outerchainUpdateTime;


    public Integer getOuterchainId() {
        return outerchainId;
    }

    public void setOuterchainId(Integer outerchainId) {
        this.outerchainId = outerchainId;
    }

    public String getOuterchainUrl() {
        return outerchainUrl;
    }

    public void setOuterchainUrl(String outerchainUrl) {
        this.outerchainUrl = outerchainUrl;
    }

    public LocalDateTime getOuterchainCreateTime() {
        return outerchainCreateTime;
    }

    public void setOuterchainCreateTime(LocalDateTime outerchainCreateTime) {
        this.outerchainCreateTime = outerchainCreateTime;
    }

    public String getOuterchainDesc() {
        return outerchainDesc;
    }

    public void setOuterchainDesc(String outerchainDesc) {
        this.outerchainDesc = outerchainDesc;
    }

    public Integer getOuterchainSiteId() {
        return outerchainSiteId;
    }

    public void setOuterchainSiteId(Integer outerchainSiteId) {
        this.outerchainSiteId = outerchainSiteId;
    }

    public String getOuterchainName() {
        return outerchainName;
    }

    public void setOuterchainName(String outerchainName) {
        this.outerchainName = outerchainName;
    }

    public LocalDateTime getOuterchainUpdateTime() {
        return outerchainUpdateTime;
    }

    public void setOuterchainUpdateTime(LocalDateTime outerchainUpdateTime) {
        this.outerchainUpdateTime = outerchainUpdateTime;
    }

    @Override
    public String toString() {
        return "Outerchain{" +
        ", outerchainId=" + outerchainId +
        ", outerchainUrl=" + outerchainUrl +
        ", outerchainCreateTime=" + outerchainCreateTime +
        ", outerchainDesc=" + outerchainDesc +
        ", outerchainSiteId=" + outerchainSiteId +
        ", outerchainName=" + outerchainName +
        ", outerchainUpdateTime=" + outerchainUpdateTime +
        "}";
    }
}
