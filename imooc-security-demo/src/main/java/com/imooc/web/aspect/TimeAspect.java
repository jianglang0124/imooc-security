package com.imooc.web.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;
/* 切面进行拦截*/
//@Aspect
//@Component
public class TimeAspect {

    // 这个类里面的任意返回值 方法 和参数类型
    @Around("execution(* com.imooc.web.controller.UserTestController.*(..))")
   public  Object hanndleControllerMethod(ProceedingJoinPoint pjp) throws  Throwable{

        System.out.println("Aspect start");

        Object[] args = pjp.getArgs();
        for (Object arg : args){
            System.out.println("arg "+ arg);
        }
        long time = new Date().getTime();
        Object object = pjp.proceed();  // 这个是方法的执行
        System.out.println("耗时==="+(new Date().getTime()-time));
        System.out.println("Aspect end");
        return  object;
    }

}
