package com.xkenmon.cms.dao.entity;

import com.baomidou.mybatisplus.annotation.*;

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
@TableName("cms_category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "category_id", type = IdType.AUTO)
    private Integer categoryId;

    private String categoryTitle;

    private LocalDateTime categoryCreateTime;

    private LocalDateTime categoryUpdateTime;

    private Integer categoryParentId;

    private Integer categoryLevel;

    private Integer categorySiteId;

    /**
     * 0代表待审核，1代表通过，2代表审核未通过
     */
    private Integer categoryStatus;

    private String categoryDesc;

    private Integer categoryOrder;

    private String categorySkin;

    private String categoryType;

    private Boolean categoryInHomepage;

    private Integer categoryHit;


    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public LocalDateTime getCategoryCreateTime() {
        return categoryCreateTime;
    }

    public void setCategoryCreateTime(LocalDateTime categoryCreateTime) {
        this.categoryCreateTime = categoryCreateTime;
    }

    public LocalDateTime getCategoryUpdateTime() {
        return categoryUpdateTime;
    }

    public void setCategoryUpdateTime(LocalDateTime categoryUpdateTime) {
        this.categoryUpdateTime = categoryUpdateTime;
    }

    public Integer getCategoryParentId() {
        return categoryParentId;
    }

    public void setCategoryParentId(Integer categoryParentId) {
        this.categoryParentId = categoryParentId;
    }

    public Integer getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(Integer categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public Integer getCategorySiteId() {
        return categorySiteId;
    }

    public void setCategorySiteId(Integer categorySiteId) {
        this.categorySiteId = categorySiteId;
    }

    public Integer getCategoryStatus() {
        return categoryStatus;
    }

    public void setCategoryStatus(Integer categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public Integer getCategoryOrder() {
        return categoryOrder;
    }

    public void setCategoryOrder(Integer categoryOrder) {
        this.categoryOrder = categoryOrder;
    }

    public String getCategorySkin() {
        return categorySkin;
    }

    public void setCategorySkin(String categorySkin) {
        this.categorySkin = categorySkin;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public Boolean getCategoryInHomepage() {
        return categoryInHomepage;
    }

    public void setCategoryInHomepage(Boolean categoryInHomepage) {
        this.categoryInHomepage = categoryInHomepage;
    }

    public Integer getCategoryHit() {
        return categoryHit;
    }

    public void setCategoryHit(Integer categoryHit) {
        this.categoryHit = categoryHit;
    }

    @Override
    public String toString() {
        return "Category{" +
                ", categoryId=" + categoryId +
                ", categoryTitle=" + categoryTitle +
                ", categoryCreateTime=" + categoryCreateTime +
                ", categoryUpdateTime=" + categoryUpdateTime +
                ", categoryParentId=" + categoryParentId +
                ", categoryLevel=" + categoryLevel +
                ", categorySiteId=" + categorySiteId +
                ", categoryStatus=" + categoryStatus +
                ", categoryDesc=" + categoryDesc +
                ", categoryOrder=" + categoryOrder +
                ", categorySkin=" + categorySkin +
                ", categoryType=" + categoryType +
                ", categoryInHomepage=" + categoryInHomepage +
                ", categoryHit=" + categoryHit +
                "}";
    }
}
