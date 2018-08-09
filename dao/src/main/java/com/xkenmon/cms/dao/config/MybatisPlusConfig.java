package com.xkenmon.cms.dao.config;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bigmeng
 */
@Configuration
@MapperScan("com.xkenmon.cms.dao.mapper/*")
public class MybatisPlusConfig {
    @Bean()
    public GlobalConfig globalConfig() {
        GlobalConfig config = new GlobalConfig();
        config.setDbConfig(new GlobalConfig.DbConfig().setColumnUnderline(true));
        return config;
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
