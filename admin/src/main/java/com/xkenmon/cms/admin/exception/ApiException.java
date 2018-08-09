package com.xkenmon.cms.admin.exception;

/**
 * @author bigmeng
 * @date 2018/8/7
 */
public class ApiException extends Exception {
    private Integer code;

    public ApiException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
