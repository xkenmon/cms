package com.xkenmon.cms.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author bigmeng
 * @date 2018/8/12
 */
@SpringBootApplication(scanBasePackages = "com.xkenmon.cms")
@EnableCaching
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
