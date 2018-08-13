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
@TableName("cms_friend_link")
public class FriendLink implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "friend_link_id", type = IdType.AUTO)
    private Integer friendLinkId;

    private String friendLinkUrl;

    private LocalDateTime friendLinkCreateTime;

    /**
     * 外链的描述
     */
    private String friendLinkDesc;

    private Integer friendLinkSiteId;

    private String friendLinkName;

    private LocalDateTime friendLinkUpdateTime;

    public Integer getFriendLinkId() {
        return friendLinkId;
    }

    public void setFriendLinkId(Integer friendLinkId) {
        this.friendLinkId = friendLinkId;
    }

    public String getFriendLinkUrl() {
        return friendLinkUrl;
    }

    public void setFriendLinkUrl(String friendLinkUrl) {
        this.friendLinkUrl = friendLinkUrl;
    }

    public LocalDateTime getFriendLinkCreateTime() {
        return friendLinkCreateTime;
    }

    public void setFriendLinkCreateTime(LocalDateTime friendLinkCreateTime) {
        this.friendLinkCreateTime = friendLinkCreateTime;
    }

    public String getFriendLinkDesc() {
        return friendLinkDesc;
    }

    public void setFriendLinkDesc(String friendLinkDesc) {
        this.friendLinkDesc = friendLinkDesc;
    }

    public Integer getFriendLinkSiteId() {
        return friendLinkSiteId;
    }

    public void setFriendLinkSiteId(Integer friendLinkSiteId) {
        this.friendLinkSiteId = friendLinkSiteId;
    }

    public String getFriendLinkName() {
        return friendLinkName;
    }

    public void setFriendLinkName(String friendLinkName) {
        this.friendLinkName = friendLinkName;
    }

    public LocalDateTime getFriendLinkUpdateTime() {
        return friendLinkUpdateTime;
    }

    public void setFriendLinkUpdateTime(LocalDateTime friendLinkUpdateTime) {
        this.friendLinkUpdateTime = friendLinkUpdateTime;
    }

    @Override
    public String toString() {
        return "FriendLink{" +
                "friendLinkId=" + friendLinkId +
                ", friendLinkUrl='" + friendLinkUrl + '\'' +
                ", friendLinkCreateTime=" + friendLinkCreateTime +
                ", friendLinkDesc='" + friendLinkDesc + '\'' +
                ", friendLinkSiteId=" + friendLinkSiteId +
                ", friendLinkName='" + friendLinkName + '\'' +
                ", friendLinkUpdateTime=" + friendLinkUpdateTime +
                '}';
    }
}
