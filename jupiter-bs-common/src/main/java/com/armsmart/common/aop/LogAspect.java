package com.armsmart.common.aop;

import com.armsmart.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.armsmart..controller..*.*(..))")
    public void logPointCut() {

    }

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //url
        log.info("url:{}", request.getRequestURL());
        //method
        log.info("method:{}", request.getMethod());
        //类方法
        log.info("class_method:{}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //参数(joinPoint.getArgs()返回一个参数数组)
        log.info("args:{}", Arrays.asList(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "object", pointcut = "logPointCut()")
    public void doAfterReturning(Object object) {
        log.info("response:{}", JsonUtil.bean2Json(object));
    }
}
