package com.xkenmon.cms.common.log;

/**
 * @author bigmeng
 * @date 2018/8/20
 */
public class WebStatisticLog {

    private Long id;

    private String type;

    private String ip;
    /**
     * 内容id
     */
    private Integer contentId;

    private Long timestamp;

    /**
     * 站点id
     */
    private Integer sid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
