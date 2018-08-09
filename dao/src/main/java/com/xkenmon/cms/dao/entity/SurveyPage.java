package com.xkenmon.cms.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 问卷调查页面
 * </p>
 *
 * @author AutoGenerator
 * @since 2018-08-06
 */
@TableName("cms_survey_page")
public class SurveyPage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "page_id", type = IdType.AUTO)
    private Integer pageId;

    private String pageTitle;

    private LocalDateTime pageCreateTime;

    private LocalDateTime pageEndTime;

    private Integer pageSiteId;

    private String pageSkinName;


    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public LocalDateTime getPageCreateTime() {
        return pageCreateTime;
    }

    public void setPageCreateTime(LocalDateTime pageCreateTime) {
        this.pageCreateTime = pageCreateTime;
    }

    public LocalDateTime getPageEndTime() {
        return pageEndTime;
    }

    public void setPageEndTime(LocalDateTime pageEndTime) {
        this.pageEndTime = pageEndTime;
    }

    public Integer getPageSiteId() {
        return pageSiteId;
    }

    public void setPageSiteId(Integer pageSiteId) {
        this.pageSiteId = pageSiteId;
    }

    public String getPageSkinName() {
        return pageSkinName;
    }

    public void setPageSkinName(String pageSkinName) {
        this.pageSkinName = pageSkinName;
    }

    @Override
    public String toString() {
        return "SurveyPage{" +
        ", pageId=" + pageId +
        ", pageTitle=" + pageTitle +
        ", pageCreateTime=" + pageCreateTime +
        ", pageEndTime=" + pageEndTime +
        ", pageSiteId=" + pageSiteId +
        ", pageSkinName=" + pageSkinName +
        "}";
    }
}
