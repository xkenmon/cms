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
@TableName("cms_sys")
public class Sys implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sysKey;

    private String sysValue;

    private LocalDateTime sysCreateTime;

    private LocalDateTime sysUpdateTime;


    public String getSysKey() {
        return sysKey;
    }

    public void setSysKey(String sysKey) {
        this.sysKey = sysKey;
    }

    public String getSysValue() {
        return sysValue;
    }

    public void setSysValue(String sysValue) {
        this.sysValue = sysValue;
    }

    public LocalDateTime getSysCreateTime() {
        return sysCreateTime;
    }

    public void setSysCreateTime(LocalDateTime sysCreateTime) {
        this.sysCreateTime = sysCreateTime;
    }

    public LocalDateTime getSysUpdateTime() {
        return sysUpdateTime;
    }

    public void setSysUpdateTime(LocalDateTime sysUpdateTime) {
        this.sysUpdateTime = sysUpdateTime;
    }

    @Override
    public String toString() {
        return "Sys{" +
        ", sysKey=" + sysKey +
        ", sysValue=" + sysValue +
        ", sysCreateTime=" + sysCreateTime +
        ", sysUpdateTime=" + sysUpdateTime +
        "}";
    }
}
