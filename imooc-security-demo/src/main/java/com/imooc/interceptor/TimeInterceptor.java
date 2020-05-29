package com.imooc.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

// 拦截器
@Component
public class TimeInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("preHandle");
       //  优点所在的地方  filter 不知道是谁处理的这个
        System.out.println(((HandlerMethod)handler).getBean().getClass().getName());
        System.out.println(((HandlerMethod)handler).getMethod().getName());
        request.setAttribute("starTime",new Date().getTime());
      // 这边要 return  true 才能进行下面的操作
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 不报异常才走这个方法
        System.out.println("postHandle");
        long  starTime =(Long) request.getAttribute("starTime");
        System.out.println("耗时===="+(new Date().getTime()-starTime));

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        System.out.println("afterCompletion");
        long  starTime =(Long) request.getAttribute("starTime");
        System.out.println("耗时===="+(new Date().getTime()-starTime));
        System.out.println("ex is "+ex);
    }
}
