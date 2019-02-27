package com.czy.api.doc.builder.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Author: czy
 * @Date: 2019/2/26 17:29
 * @Version 1.0
 */
@Component
@Aspect
public class GeneratorAspect {

    @Around(value = "execution(* com.czy.api.doc.builder.generator.impl.DocGeneratorImpl.generateVoDoc(..))")
    public Object generateVoDocAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("开始生成Vo...");
        Object object = proceedingJoinPoint.proceed();
        System.out.println("生成Vo结束...");

        return object;
    }

    @Around(value = "execution(* com.czy.api.doc.builder.generator.impl.DocGeneratorImpl.generateParameterDoc(..))")
    public Object generateParameterDocAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("开始生成Parameter...");
        Object object = proceedingJoinPoint.proceed();
        System.out.println("生成Parameter结束...");

        return object;
    }

    @Around(value = "execution(* com.czy.api.doc.builder.generator.impl.DocGeneratorImpl.generateApiDoc(..))")
    public Object generateApiDocAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("开始生成API文档...");
        Object object = proceedingJoinPoint.proceed();
        System.out.println("生成API文档结束...");

        return object;
    }

}
