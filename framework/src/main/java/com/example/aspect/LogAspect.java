package com.example.aspect;

import com.alibaba.fastjson.JSON;
import com.example.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 自定义切面类
 * @Author：hanxiaojian
 * @Date：2023/4/13 10:59
 */
@Component
@Aspect
@Slf4j
public class LogAspect {
    /**
     * 定义切点
     */
    @Pointcut("@annotation(com.example.annotation.SystemLog)")
    public void pt() {

    }

    /**
     * 打印日志
     * @param joinPoint  参数表示执行目标方法封装之后的对象
     * @return
     */
    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable{
        // 执行该对象
        Object proceed;
        try {
            handlerBefore(joinPoint);
            // 执行被增强方法
            proceed = joinPoint.proceed();
            handlerAfter(proceed);
        } finally {
            // 结束后换行
            log.info("=======End=======" + System.lineSeparator());
        }
        return proceed;
    }

    private void handlerAfter(Object proceed) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(proceed));
    }

    private void handlerBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes request = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = request.getRequest();

        // 获取被增强方法的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}", httpServletRequest.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}", systemLog.bussinessName());
        // 打印 Http method
        log.info("HTTP Method    : {}", httpServletRequest.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), ((MethodSignature)joinPoint.getSignature()).getName());
        // 打印请求的 IP
        log.info("IP             : {}", httpServletRequest.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));

    }
    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SystemLog systemLog = method.getAnnotation(SystemLog.class);
        return systemLog;
    }
}
