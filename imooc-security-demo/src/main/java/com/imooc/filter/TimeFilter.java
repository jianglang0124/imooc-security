package com.imooc.filter;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;
// 过滤器
// @Component
public class TimeFilter  implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Time filter init ");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("filter time start");
        long time = new Date().getTime();
        filterChain.doFilter(request,response);
        System.out.println("耗时==="+(new Date().getTime()-time));
    }

    @Override
    public void destroy() {
        System.out.println(" time filter destroy");
    }
}
