package com.xkenmon.cms.web.aspect;

import com.xkenmon.cms.common.log.WebStatisticLog;
import com.xkenmon.cms.common.log.WebStatisticLogRepository;
import com.xkenmon.cms.common.utils.SequenceGenerator;
import com.xkenmon.cms.web.ApplicationContextProvider;
import com.xkenmon.cms.web.annotation.AccessCount;
import com.xkenmon.cms.web.util.IpUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 对{@link AccessCount}进行横切，统计数据
 *
 * @author bigmeng
 */
@Component
@Aspect
public class CountAspect {

    private final WebStatisticLogRepository logRepository;

    private static final DefaultParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    @Autowired
    public CountAspect(WebStatisticLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Pointcut("@annotation(com.xkenmon.cms.web.annotation.AccessCount)")
    public void countPoint() {
    }

    @AfterReturning(value = "countPoint()", returning = "ret")
    public void count(JoinPoint point, Objects ret) {
        // 获取横切的方法
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();

        // 获取请求体
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                        .getRequest();
        Objects.requireNonNull(request);

        // 拿到方法上的注解
        AccessCount accessCount = method.getAnnotation(AccessCount.class);

        // 解析SpEL表达式
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        String[] paramNames = NAME_DISCOVERER.getParameterNames(method);
        // 设置beanResolver，可以在SpEL中使用`@`来调用bean
        context.setBeanResolver(new BeanFactoryResolver(ApplicationContextProvider.getApplicationContext()));
        if (paramNames != null) {
            for (int i = 0; i < paramNames.length; i++) {
                context.setVariable(paramNames[i], point.getArgs()[i]);
            }
        }
        context.setVariable("ret", ret);
        Expression expression = parser.parseExpression(accessCount.contentId());
        Integer contentId = expression.getValue(Integer.class);
        expression = parser.parseExpression(accessCount.siteId());
        Integer siteId = expression.getValue(Integer.class);

        //写入日志
        WebStatisticLog log = new WebStatisticLog();
        log.setId(SequenceGenerator.nextId());
        log.setIp(IpUtil.getRealIP(request));
        log.setTimestamp(System.currentTimeMillis());
        log.setContentId(contentId);
        log.setSid(siteId);
        log.setType(accessCount.countType().toString());

        logRepository.insert(log);
    }
}
