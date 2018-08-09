package com.xkenmon.cms.admin.api;

import com.xkenmon.cms.admin.dto.ApiMessage;
import com.xkenmon.cms.admin.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author bigmeng
 * @date 2018/8/7
 */
@RestControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(ApiException.class)
    public ApiMessage handleApiException(HttpServletRequest request, HttpServletResponse response, ApiException exception) {
        LOGGER.info("IP: {}\tMETHOD: {}, URI: {}, QUERY: {}\tCODE: {}, MSG: {}",
                request.getRemoteHost(),
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString(),
                exception.getCode(),
                exception.getMessage());
        response.setStatus(exception.getCode());
        return ApiMessage.fail(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiMessage handleGlobalException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        LOGGER.error("IP: {}\tMETHOD: {}, URI: {}, QUERY: {}\tMSG: {}",
                request.getRemoteHost(),
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString(),
                exception.fillInStackTrace());
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return ApiMessage.fail(500, exception.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ApiMessage handleDataIntegrityViolationException(HttpServletRequest request, HttpServletResponse resp, DataIntegrityViolationException e) {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return ApiMessage.fail(400, "data integrity violation, check constraint fields");
    }
}
