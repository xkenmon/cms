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
@TableName("cms_module")
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "module_id", type = IdType.AUTO)
    private Integer moduleId;

    private String moduleName;

    private String moduleUrl;

    private Integer moduleParentId;

    private Integer moduleSort;

    private LocalDateTime moduleCreateTime;


    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleUrl() {
        return moduleUrl;
    }

    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl;
    }

    public Integer getModuleParentId() {
        return moduleParentId;
    }

    public void setModuleParentId(Integer moduleParentId) {
        this.moduleParentId = moduleParentId;
    }

    public Integer getModuleSort() {
        return moduleSort;
    }

    public void setModuleSort(Integer moduleSort) {
        this.moduleSort = moduleSort;
    }

    public LocalDateTime getModuleCreateTime() {
        return moduleCreateTime;
    }

    public void setModuleCreateTime(LocalDateTime moduleCreateTime) {
        this.moduleCreateTime = moduleCreateTime;
    }

    @Override
    public String toString() {
        return "Module{" +
        ", moduleId=" + moduleId +
        ", moduleName=" + moduleName +
        ", moduleUrl=" + moduleUrl +
        ", moduleParentId=" + moduleParentId +
        ", moduleSort=" + moduleSort +
        ", moduleCreateTime=" + moduleCreateTime +
        "}";
    }
}
