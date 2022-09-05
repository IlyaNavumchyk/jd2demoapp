package com.jd2.aop;


import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class CustomAspect {

    private static final Logger LOG = Logger.getLogger(CustomAspect.class);

    @Pointcut("execution(* com.jd2.repository.jdbstamplates.JdbcTemplateUserRepository.*(..))")
    public void aroundRepositoryPointcut() {
    }

    @Around("aroundRepositoryPointcut()")
    public Object logAroundMethods(ProceedingJoinPoint joinPoint) throws Throwable {

        LOG.info("Method " + joinPoint.getSignature().getName() + " start");

        StopWatch stopWatch = new StopWatch(joinPoint.toString());

        stopWatch.start();

        Object proceed = joinPoint.proceed();

        stopWatch.stop();

        LOG.info("Method " + joinPoint.getSignature().getName() +
                " finished in " + stopWatch.getTotalTimeMillis() + " ms");

        return proceed;
    }

    public static final Map<String, Integer> methodUsageStatistics = new HashMap<>();

    @Around("aroundRepositoryPointcut()")
    public Object getMethodUsageStatistics(ProceedingJoinPoint joinPoint) throws Throwable {

        String declaringTypeName = joinPoint.getSignature().getName();

        if (!methodUsageStatistics.containsKey(declaringTypeName)) {
            methodUsageStatistics.put(declaringTypeName, 1);
        } else {
            methodUsageStatistics.put(declaringTypeName, methodUsageStatistics.get(declaringTypeName + 1));
        }

        return joinPoint.proceed();
    }
}
