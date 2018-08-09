package com.xkenmon.cms.admin.dto;

/**
 * @author bigmeng
 * @date 2018/8/7
 */
public class ApiMessage<T> {
    private Integer code;
    private String msg;
    private T data;

    public static final Integer SUCCESS = 200;

    public ApiMessage(Integer code, String msg) {
        this(code, msg, null);
    }

    public ApiMessage(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ApiMessage success(T data) {
        return new ApiMessage<>(SUCCESS, "success", data);
    }

    public static ApiMessage fail(Integer code,String msg){
        return new ApiMessage(code,msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
