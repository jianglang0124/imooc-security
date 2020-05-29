package com.imooc.config;


import com.imooc.filter.TimeFilter;
import com.imooc.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;

// @Configuration
public class WebConfig  extends WebMvcConfigurerAdapter {


    // 这边拦截器需要继承一个 WebMvcConfigurerAdapter
    @Autowired
    TimeInterceptor timeInterceptor;

    // 重写这个方法 讲 自己定义的拦截器 进行注册
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor);
    }

    // 加载第三方的 filter 类的时候通过配置的方式来进行管理
    @Bean
    public FilterRegistrationBean timeFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();

        TimeFilter timeFilter = new TimeFilter();

        registrationBean.setFilter(timeFilter);

        ArrayList<String> urls = new ArrayList<>();
        // 加载第三方的filter的时候 可以指定过滤的路径
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);
        return  registrationBean;



    }



}
