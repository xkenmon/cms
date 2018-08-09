package com.xkenmon.cms.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("cms_skin")
public class Skin implements Serializable {

    private static final long serialVersionUID = 1L;

    private String skinName;

    private LocalDateTime skinCreateTime;

    private LocalDateTime skinUpdateTime;


    public String getSkinName() {
        return skinName;
    }

    public void setSkinName(String skinName) {
        this.skinName = skinName;
    }

    public LocalDateTime getSkinCreateTime() {
        return skinCreateTime;
    }

    public void setSkinCreateTime(LocalDateTime skinCreateTime) {
        this.skinCreateTime = skinCreateTime;
    }

    public LocalDateTime getSkinUpdateTime() {
        return skinUpdateTime;
    }

    public void setSkinUpdateTime(LocalDateTime skinUpdateTime) {
        this.skinUpdateTime = skinUpdateTime;
    }

    @Override
    public String toString() {
        return "Skin{" +
        ", skinName=" + skinName +
        ", skinCreateTime=" + skinCreateTime +
        ", skinUpdateTime=" + skinUpdateTime +
        "}";
    }
}
