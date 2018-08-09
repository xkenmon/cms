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
@TableName("cms_survey_topic")
public class SurveyTopic implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "topic_id", type = IdType.AUTO)
    private Integer topicId;

    private String topicQuestion;

    private String topicType;

    private Integer topicPageId;


    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getTopicQuestion() {
        return topicQuestion;
    }

    public void setTopicQuestion(String topicQuestion) {
        this.topicQuestion = topicQuestion;
    }

    public String getTopicType() {
        return topicType;
    }

    public void setTopicType(String topicType) {
        this.topicType = topicType;
    }

    public Integer getTopicPageId() {
        return topicPageId;
    }

    public void setTopicPageId(Integer topicPageId) {
        this.topicPageId = topicPageId;
    }

    @Override
    public String toString() {
        return "SurveyTopic{" +
        ", topicId=" + topicId +
        ", topicQuestion=" + topicQuestion +
        ", topicType=" + topicType +
        ", topicPageId=" + topicPageId +
        "}";
    }
}
