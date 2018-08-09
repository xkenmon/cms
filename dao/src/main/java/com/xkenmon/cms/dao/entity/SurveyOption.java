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
@TableName("cms_survey_option")
public class SurveyOption implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "option_id", type = IdType.AUTO)
    private Integer optionId;

    private String optionContent;

    private Integer topicId;

    private Integer optionCount;


    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getOptionCount() {
        return optionCount;
    }

    public void setOptionCount(Integer optionCount) {
        this.optionCount = optionCount;
    }

    @Override
    public String toString() {
        return "SurveyOption{" +
        ", optionId=" + optionId +
        ", optionContent=" + optionContent +
        ", topicId=" + topicId +
        ", optionCount=" + optionCount +
        "}";
    }
}
