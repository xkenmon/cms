package com.xkenmon.cms.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author AutoGenerator
 * @since 2018-08-06
 */
@TableName("cms_file")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer fileArticleId;

    private String fileKey;

    private Integer fileSiteId;


    public Integer getFileArticleId() {
        return fileArticleId;
    }

    public void setFileArticleId(Integer fileArticleId) {
        this.fileArticleId = fileArticleId;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public Integer getFileSiteId() {
        return fileSiteId;
    }

    public void setFileSiteId(Integer fileSiteId) {
        this.fileSiteId = fileSiteId;
    }

    @Override
    public String toString() {
        return "File{" +
        ", fileArticleId=" + fileArticleId +
        ", fileKey=" + fileKey +
        ", fileSiteId=" + fileSiteId +
        "}";
    }
}
