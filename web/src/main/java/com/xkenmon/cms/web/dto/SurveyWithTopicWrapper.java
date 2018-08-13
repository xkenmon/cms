package com.xkenmon.cms.web.dto;


import com.xkenmon.cms.dao.entity.SurveyPage;

import java.util.List;

public class SurveyWithTopicWrapper {
    private SurveyPage page;
    private List<TopicWithOptionWrapper> topicList;

    public List<TopicWithOptionWrapper> getTopicList() {
        return topicList;
    }

    public SurveyWithTopicWrapper(SurveyPage page, List<TopicWithOptionWrapper> topicList) {
        this.page = page;
        this.topicList = topicList;
    }

    public void setTopicList(List<TopicWithOptionWrapper> topicList) {
        this.topicList = topicList;
    }

    public SurveyPage getPage() {
        return page;
    }

    public void setPage(SurveyPage page) {
        this.page = page;
    }

    public SurveyWithTopicWrapper(List<TopicWithOptionWrapper> topicList, SurveyPage page) {
        this.topicList = topicList;
        this.page = page;
    }

    public SurveyWithTopicWrapper() {
    }
}
