package com.mungwin.payngwinteller.security.security.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class PreApproveMethodAdvice {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Before("@annotation(PreApprove)")
    public void approveMethod(JoinPoint joinPoint) throws Throwable {
        PreApprove preApprove = ((MethodSignature) joinPoint.getSignature())
                .getMethod().getAnnotation(PreApprove.class);
        List<String> authorities = Arrays.asList(preApprove.value());
        logger.info("authorities {}", authorities);
        preApprove.handler().getConstructor().newInstance().doApprove(authorities);
    }

}
