package com.xkenmon.cms.admin.aspect;

import com.xkenmon.cms.admin.ApplicationContextProvider;
import com.xkenmon.cms.admin.annotation.Auth;
import com.xkenmon.cms.admin.auth.UserPrincipal;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * @author bigmeng
 * @date 2018/8/13
 */
@Aspect
@Component
public class AuthAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthAspect.class);

    private static final DefaultParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    @Pointcut("@annotation(com.xkenmon.cms.admin.annotation.Auth)")
    public void authPointCut() {
    }

    @Around("authPointCut()")
    public Object doBeforeAdvice(ProceedingJoinPoint point) throws Throwable {
        // 用户权限列表
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorityCollection = authentication.getAuthorities();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        methodSignature.getParameterNames();
        Auth annotation = method.getAnnotation(Auth.class);
        String el = annotation.siteId();
        Integer sid = getSiteIdFromSpEl(el, method, point.getArgs());
        String[] moduleNames = annotation.modules();
        if (sid == null) {
            LOGGER.error("can't get siteId, please check your @Auth annotation");
            return null;
        }
        LOGGER.info("auth - user name: {}, site id: {}, moduleNames: {}", userPrincipal.getUsername(), sid, moduleNames);

        boolean ok = authorityCollection.stream()
                .anyMatch(
                        auth -> Arrays.stream(moduleNames)
                                .allMatch(moduleName -> (sid + "::" + moduleName).equals(auth.getAuthority()))
                );
        if (!ok) {
            LOGGER.info("User [{}] attempting to access an unauthorized api has been blocked"
                    , userPrincipal.getUsername());
            HttpServletResponse response =
                    ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                            .getResponse();
            Objects.requireNonNull(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        return point.proceed();
    }

    private Integer getSiteIdFromSpEl(String el, Method method, Object[] args) {
        ExpressionParser parser = new SpelExpressionParser();
        // TODO: 2018/8/13 Context 的创建消耗资源较大，能不能重用?
        StandardEvaluationContext context = new StandardEvaluationContext();
        String[] paramNames = NAME_DISCOVERER.getParameterNames(method);
        if (paramNames == null) {
            return null;
        }
        // 设置beanResolver，可以在SpEL中使用`@`来调用bean
        context.setBeanResolver(new BeanFactoryResolver(ApplicationContextProvider.getApplicationContext()));
        for (int i = 0; i < paramNames.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        Expression expression = parser.parseExpression(el);
        return expression.getValue(context, Integer.class);
    }
}
