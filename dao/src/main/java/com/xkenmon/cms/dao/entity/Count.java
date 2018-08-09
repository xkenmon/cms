package com.xkenmon.cms.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author AutoGenerator
 * @since 2018-08-06
 */
@TableName("cms_count")
public class Count implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "count_id", type = IdType.AUTO)
    private Integer countId;

    private String countType;

    private String countCid;

    private Integer countPv;

    private Long countTime;

    private Integer countInterval;


    public Integer getCountId() {
        return countId;
    }

    public void setCountId(Integer countId) {
        this.countId = countId;
    }

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }

    public String getCountCid() {
        return countCid;
    }

    public void setCountCid(String countCid) {
        this.countCid = countCid;
    }

    public Integer getCountPv() {
        return countPv;
    }

    public void setCountPv(Integer countPv) {
        this.countPv = countPv;
    }

    public Long getCountTime() {
        return countTime;
    }

    public void setCountTime(Long countTime) {
        this.countTime = countTime;
    }

    public Integer getCountInterval() {
        return countInterval;
    }

    public void setCountInterval(Integer countInterval) {
        this.countInterval = countInterval;
    }

    @Override
    public String toString() {
        return "Count{" +
        ", countId=" + countId +
        ", countType=" + countType +
        ", countCid=" + countCid +
        ", countPv=" + countPv +
        ", countTime=" + countTime +
        ", countInterval=" + countInterval +
        "}";
    }
}
