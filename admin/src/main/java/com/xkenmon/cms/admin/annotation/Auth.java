package com.xkenmon.cms.admin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author bigmeng
 * @date 2018/8/13
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
    /**
     * SpEL 表达式
     */
    String siteId();

    String[] modules();
}
