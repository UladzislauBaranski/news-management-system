package com.gmail.vladbaransky.newsmanagementsystem.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Pointcut("execution(public * com.gmail.vladbaransky.newsmanagementsystem.controller.CommentController.*(..))")
    public void callAspectOnCommentControllers() {
    }

    @Pointcut("execution(public * com.gmail.vladbaransky.newsmanagementsystem.controller.NewsCommentController.*(..))")
    public void callAspectOnNewsCommentControllers() {
    }

    @Pointcut("execution(public * com.gmail.vladbaransky.newsmanagementsystem.controller.NewsController.*(..))")
    public void callAspectOnNewsControllers() {
    }


/*
    @Before("callAspectOnControllers()")
    public void beforeAdvice(JoinPoint jp) {
        Object[] args = jp.getArgs();
        System.out.println("start");
    }
*/

  /*  @After("callAspectOnControllers()")
    public void afterAdvice(JoinPoint jp) {
        jp.getArgs();
        System.out.println("end");
    }*/

    @Around("callAspectOnCommentControllers()")
    public Object aroundCommentController(ProceedingJoinPoint jp) throws Throwable {
        return aroundAdvice(jp);
    }

    @Around("callAspectOnNewsCommentControllers()")
    public Object aroundNewsCommentController(ProceedingJoinPoint jp) throws Throwable {
        return aroundAdvice(jp);
    }

    @Around("callAspectOnNewsControllers()")
    public Object aroundNewsController(ProceedingJoinPoint jp) throws Throwable {
        return aroundAdvice(jp);
    }

    private Object aroundAdvice(ProceedingJoinPoint jp) throws Throwable {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        Object[] args = jp.getArgs();
        logger.info("Method: " + "'" + methodName + "'" + " from class: " + "'" + className + "' called");
        logger.info("Enter arguments: " + Arrays.toString(args));

        Object proceed = jp.proceed();
        logger.info("Result of work method: " + proceed);
        return proceed;
    }
}
