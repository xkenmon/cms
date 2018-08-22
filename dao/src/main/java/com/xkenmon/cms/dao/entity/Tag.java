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
@TableName("cms_tag")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "tag_id", type = IdType.AUTO)
    private Integer tagId;

    private String tagName;

    private Integer tegSiteId;

    private LocalDateTime tagCreateTime;


    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public LocalDateTime getTagCreateTime() {
        return tagCreateTime;
    }

    public void setTagCreateTime(LocalDateTime tagCreateTime) {
        this.tagCreateTime = tagCreateTime;
    }

    public Integer getTegSiteId() {
        return tegSiteId;
    }

    public void setTegSiteId(Integer tegSiteId) {
        this.tegSiteId = tegSiteId;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                ", tegSiteId=" + tegSiteId +
                ", tagCreateTime=" + tagCreateTime +
                '}';
    }
}
