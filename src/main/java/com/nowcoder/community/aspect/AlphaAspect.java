package com.nowcoder.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class AlphaAspect {
//                           service包中            所有的类    .*(..)表示类中所有的方法
    @Pointcut("execution(* com.nowcoder.community.service.*.*(..))")
    public void pointcut(){

    }

    //在连接点之前调用
    @Before("pointcut()")
    public void before(){
        System.out.println("before");
    }
    //在连接点之后
    @After("pointcut()")
    public void after(){
        System.out.println("after");
    }
    //在返回值之后
    @AfterReturning("pointcut()")
    public void afterReturning(){
        System.out.println("afterReturning");
    }
    //在抛异常的时候
    @AfterThrowing("pointcut()")
    public void afterThrowing(){
        System.out.println("afterThrowing");
    }
    //在连接点前后
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint)throws Throwable{
        //调用模板
        System.out.println("aroundBefore");
        Object obj =  joinPoint.proceed();
        System.out.println("aroundAfter");
        return obj;
    }
}
