package com.xkenmon.cms.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author bigmeng
 */
@EnableCaching
@EnableMongoRepositories(basePackages = "com.xkenmon.cms")
@SpringBootApplication(scanBasePackages = "com.xkenmon.cms")
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
