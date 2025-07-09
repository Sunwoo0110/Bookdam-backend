package com.sunwoo.bookdam.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Slf4j
public class RequestLoggerAspect {

    // 실제 컨트롤러 패키지에 맞게 조정!
    @Pointcut("execution(* com.sunwoo.bookdam..*Controller.*(..))")
    public void controllerPointCut() {}

    @Around("controllerPointCut()")
    public Object logControllerRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        // 안전하게 Request 꺼내기
        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        if (!(attrs instanceof ServletRequestAttributes servletAttrs)) {
            return joinPoint.proceed();
        }
        HttpServletRequest request = servletAttrs.getRequest();

        String controllerName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        Map<String, Object> logMap = new HashMap<>();
        logMap.put("controller", controllerName);
        logMap.put("method", methodName);
        logMap.put("params", getParamsMap(request));
        logMap.put("log_time", Instant.now());
        logMap.put("request_uri", request.getRequestURI());
        logMap.put("http_method", request.getMethod());

        log.info("API REQUEST : {}", logMap);

        return joinPoint.proceed();
    }

    private Map<String, Object> getParamsMap(HttpServletRequest request) {
        Map<String, Object> paramMap = new HashMap<>();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            String safeParam = param.replaceAll("\\.", "-");
            if (safeParam.equalsIgnoreCase("password")) {
                paramMap.put(safeParam, "*****");
            } else {
                paramMap.put(safeParam, request.getParameter(param));
            }
        }
        return paramMap;
    }
}
