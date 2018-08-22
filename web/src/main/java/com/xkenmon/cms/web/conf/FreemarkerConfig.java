package com.xkenmon.cms.web.conf;

import com.xkenmon.cms.dao.entity.Sys;
import com.xkenmon.cms.dao.mapper.SysMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bigmeng
 */
@Configuration
public class FreemarkerConfig implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreemarkerConfig.class);

    private final
    SysMapper sysMapper;

    @Autowired
    public FreemarkerConfig(SysMapper sysMapper) {
        this.sysMapper = sysMapper;
    }

    @Bean
    public FreeMarkerConfigurer loadPathConfig() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        final String defaultSkinPath = "classpath:templates";
        String dbSkinPath = "";
        // TODO: 2018/8/12 const field
        Sys skinPathEntry = sysMapper.selectById("skinPath");
        if (skinPathEntry != null) {
            dbSkinPath = skinPathEntry.getSysValue();
        }
        if (!dbSkinPath.isEmpty()) {
            configurer.setTemplateLoaderPaths(defaultSkinPath, dbSkinPath);
        } else {
            configurer.setTemplateLoaderPath(defaultSkinPath);
        }
        LOGGER.info("Gangster CMS : templates path = {},{}", defaultSkinPath, dbSkinPath);

        configurer.setDefaultEncoding("UTF-8");
        return configurer;
    }

    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setPrefix("");
        resolver.setSuffix(".ftl");
        resolver.setContentType("text/html; charset=UTF-8");
        resolver.setRequestContextAttribute("ctx");
        return resolver;
    }

}