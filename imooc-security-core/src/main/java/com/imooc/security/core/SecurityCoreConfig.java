package com.imooc.security.core;

import com.imooc.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
/*
 * @Description
 *  @EnableConfigurationProperties注解的作用是：让使用 @ConfigurationProperties 注解的类生效
 * @Date 10:23 2020/5/21
 * @Param
 * @return
 **/
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
}
