package com.xkenmon.cms.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author bigmeng
 */
@SpringBootApplication(scanBasePackages = {"com.xkenmon.cms"})
@MapperScan("com.xkenmon.cms.dao.mapper")
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}
}
