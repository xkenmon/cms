package com.xkenmon.cms.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author bigmeng
 */
@EnableCaching
@SpringBootApplication(scanBasePackages = "com.xkenmon.cms")
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
