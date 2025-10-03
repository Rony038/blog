package com.practice.blog.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExceptionLoggingAspect {
    @Pointcut("within(com.practice.blog.exceptions.GlobalExceptionHandler)")
    public void exceptionHandlerMethods() {}


    @AfterReturning(pointcut = "exceptionHandlerMethods()", returning = "result")
    public void logAfterHandling(JoinPoint joinPoint, ResponseEntity<?> result){
        String handlerMethod = joinPoint.getSignature().getName();
        HttpStatusCode status = result.getStatusCode();
        Object body = result.getBody();
        if (status.is5xxServerError()) {
            log.error("Internal Server Error Handled: {} returned status {} - {}",
                    handlerMethod, status, body);
        } else if (status.is4xxClientError()) {
            log.warn("Client Error Handled: {} returned status {} - {}",
                    handlerMethod, status, body);
        } else {
            log.info("Exception Handler executed: {} returned status {}",
                    handlerMethod, status);
        }
    }
}
