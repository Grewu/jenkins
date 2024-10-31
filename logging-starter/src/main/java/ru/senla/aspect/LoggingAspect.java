package ru.senla.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * The {@code LoggingAspect} class is an Aspect that implements logging
 * functionality for methods and classes annotated with {@code @Logging}.
 *
 * <p>
 * This aspect uses AspectJ to intercept method calls and log
 * relevant information before and after the method execution.
 * </p>
 */
@Slf4j
@Aspect
public class LoggingAspect {

    @Pointcut("@annotation(ru.senla.annotation.Logging)")
    private void annotationPointcut() {
    }

    @Pointcut("@within(ru.senla.annotation.Logging)")
    private void loggingByType() {
    }

    @Around("annotationPointcut() || loggingByType()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String method = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("Before:  {}.{}({})", className, method, args);

        Object result = joinPoint.proceed();

        log.info("After:   result::{}", result);
        return result;
    }
}
