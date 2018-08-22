package com.xkenmon.cms.dao.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import org.junit.Test;

public class GenerateUtilTest {
    //    @Test
    public static void main(String[] args) {
        DataSourceConfig config = new DataSourceConfig();
        config.setDbType(DbType.MYSQL);
        config.setDriverName("com.mysql.cj.jdbc.Driver");
        config.setUrl("jdbc:mysql://db_server:3306/db_cms_new");
        config.setUsername("root");
        config.setPassword("$%^YGR^&UHGT^&U");

        String[] tableNames = new String[]{
                "cms_article",
                "cms_category",
                "cms_count",
                "cms_file",
                "cms_log",
                "cms_mail",
                "cms_module",
                "cms_outerchain",
                "cms_permission",
                "cms_site",
                "cms_skin",
                "cms_survey_option",
                "cms_survey_page",
                "cms_survey_topic",
                "cms_sys",
                "cms_tag",
                "cms_user",
                "cms_tag_article",
        };

        GenerateUtil.generateByTables(config, "com.xkenmon.cms.dao", tableNames);
    }
}
