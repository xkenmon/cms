package com.xkenmon.cms.web.aspect;

import com.xkenmon.cms.common.log.WebAccessLog;
import com.xkenmon.cms.common.utils.SequenceGenerator;
import com.xkenmon.cms.common.log.WebAccessLogRepository;
import com.xkenmon.cms.web.util.IpUtil;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 该类负责对{@link com.xkenmon.cms.web.annotation.AccessLogger}横切，记录访问日志
 *
 * @author bigmeng
 */
@Component
@Aspect
public class AccessLogAspect {

    private final WebAccessLogRepository logRepository;

    private final Logger logger = LoggerFactory.getLogger("Access log");

    private static final ThreadLocal<Long> TIME_COUNT = new ThreadLocal<>();

    @Autowired
    public AccessLogAspect(WebAccessLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Pointcut("@annotation(com.xkenmon.cms.web.annotation.AccessLogger)")
    public void loggerService() {
    }

    @Before("loggerService()")
    public void doBeforeAdvice() {
        TIME_COUNT.set(System.currentTimeMillis());
    }

    @After("loggerService()")
    public void doAfterAdvice() {
        Long now = System.currentTimeMillis();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        WebAccessLog log = new WebAccessLog();
        log.setId(SequenceGenerator.nextId());
        log.setIp(IpUtil.getRealIP(request));
        log.setTimeCost(now - TIME_COUNT.get());
        log.setUrl(request.getRequestURL().toString());
        log.setTimestamp(now);

        logger.info("access url: {}", request.getRequestURL().toString());
        logRepository.insert(log);
    }
}
