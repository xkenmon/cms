package com.xkenmon.cms.admin.api;

import com.xkenmon.cms.admin.dto.ApiMessage;
import com.xkenmon.cms.admin.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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

    /**
     * service 抛出的Api异常
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiMessage> handleApiException(HttpServletRequest request, ApiException exception) {
        LOGGER.info("IP: {}\tMETHOD: {}, URI: {}, QUERY: {}\tCODE: {}, MSG: {}",
                request.getRemoteHost(),
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString(),
                exception.getCode(),
                exception.getMessage());
        return ResponseEntity.status(exception.getCode())
                .body(ApiMessage.fail(exception.getCode(), exception.getMessage()));
    }

    /**
     * HTTP方法访问错误
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiMessage> handleMethodNotSupportException(
            HttpServletRequest request,
            HttpRequestMethodNotSupportedException exception) {
        LOGGER.info("IP: {}\tMETHOD: {}, URI: {}, QUERY: {}\tCODE: {}, MSG: {}",
                request.getRemoteHost(),
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString(),
                HttpStatus.METHOD_NOT_ALLOWED,
                exception.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ApiMessage.fail(HttpServletResponse.SC_METHOD_NOT_ALLOWED, exception.getMessage()));
    }

    /**
     * 凭据错误
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity handleBadCredentialsException(HttpServletRequest request, HttpServletResponse resp) {
        LOGGER.info("用户密码错误 - ip: {}", request.getRemoteHost());
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiMessage.fail(HttpStatus.UNAUTHORIZED, "凭据错误, BadCredentialsException"));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiMessage> handleMissParamException(HttpServletRequest request, MissingServletRequestParameterException e) {
        LOGGER.info("miss request param - ip: {}, message: {}", request.getRemoteHost(), e.getMessage());
        return ResponseEntity.badRequest().body(ApiMessage.fail(HttpServletResponse.SC_BAD_REQUEST, e.getMessage()));
    }

    /**
     * 输入数据违反数据完整性
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiMessage> handleDataIntegrityViolationException(HttpServletResponse resp, DataIntegrityViolationException e) {
        LOGGER.info("用户输入非法，DataIntegrityViolationException - {}", e.getMessage());
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return ResponseEntity.badRequest()
                .body(ApiMessage.fail(400, "data integrity violation, check constraint fields"));
    }

    /**
     * 全局异常捕获
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleGlobalException(HttpServletRequest request, Exception exception) {
        LOGGER.error("IP: {}\tMETHOD: {}, URI: {}, QUERY: {}\tMSG: ",
                request.getRemoteHost(),
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString(),
                exception.fillInStackTrace());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiMessage.fail(500, exception.getMessage()));
    }
}
