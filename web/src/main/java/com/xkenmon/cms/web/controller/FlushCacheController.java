package com.xkenmon.cms.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 在测试时使用， 访问url刷新内部缓存
 *
 * @author bigmeng
 */
@Controller
public class FlushCacheController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlushCacheController.class);

    @GetMapping("/test/flush")
    @CacheEvict(value = {"articleModel", "categoryModel", "siteModel"}, allEntries = true)
    public void flush() {
        LOGGER.info("cache flush!");
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
