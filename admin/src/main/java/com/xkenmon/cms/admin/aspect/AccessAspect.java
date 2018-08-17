package com.xkenmon.cms.admin.aspect;

import com.xkenmon.cms.admin.auth.UserPrincipal;
import com.xkenmon.cms.admin.log.LogRepository;
import com.xkenmon.cms.admin.log.MongoAdminLog;
import com.xkenmon.cms.common.constant.CmsLog;
import com.xkenmon.cms.common.utils.SequenceGenerator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author bigmeng
 * @date 2018/8/16
 */
@Aspect
@Component
public class AccessAspect {
    private final LogRepository logRepository;

    public AccessAspect(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Pointcut(
            "execution(* com.xkenmon.cms.admin.api.*.*(..))&&" +
                    "@target(org.springframework.web.bind.annotation.RequestMapping)")
    public void accessPointCut() {
    }

    @AfterReturning("accessPointCut()")
    public void doAfterReturn(JoinPoint point) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String username = "unknown";
        if (principal instanceof String) {
            username = ((String) principal);
        } else if (principal instanceof UserPrincipal) {
            username = ((UserPrincipal) principal).getUsername();
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        MongoAdminLog mongoLog = new MongoAdminLog();
        mongoLog.setId(SequenceGenerator.nextId());
        mongoLog.setIp(request.getRemoteAddr());
        mongoLog.setLevel(CmsLog.LEVEL_INFO);
        mongoLog.setTimestamp(System.currentTimeMillis());
        mongoLog.setUser(username);
        mongoLog.setUri(request.getRequestURI());
        mongoLog.setMethod(request.getMethod());
        mongoLog.setMsg(point.getTarget().toString());

        logRepository.insert(mongoLog);
    }

    @AfterThrowing(pointcut = "accessPointCut()", throwing = "e")
    public void doAfterThrow(Throwable e) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String username = "unknown";
        if (principal instanceof String) {
            username = ((String) principal);
        } else if (principal instanceof UserPrincipal) {
            username = ((UserPrincipal) principal).getUsername();
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        MongoAdminLog mongoLog = new MongoAdminLog();
        mongoLog.setId(SequenceGenerator.nextId());
        mongoLog.setIp(request.getRemoteAddr());
        mongoLog.setLevel(CmsLog.LEVEL_WARN);
        mongoLog.setTimestamp(System.currentTimeMillis());
        mongoLog.setUser(username);
        mongoLog.setUri(request.getRequestURI());
        mongoLog.setMsg(request.getMethod());
        mongoLog.setMsg(e.getMessage());

        logRepository.insert(mongoLog);
    }
}
