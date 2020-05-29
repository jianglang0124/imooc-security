package com.imooc.security.browser;

import com.imooc.security.core.authentication.AbstractChannelSecurityConfig;
import com.imooc.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.imooc.security.core.properties.SecurityConstants;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.ValidateCodeFilter;
import com.imooc.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
//  WebSecurityConfigurerAdapter

@Configuration
public class BrowserSecurityConfig  extends AbstractChannelSecurityConfig {

    /*@Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;*/

     @Autowired
    private SecurityProperties securityProperties;

     @Autowired
     private DataSource dataSource;

     @Autowired
     private  MyUserDetailsService userDetailsService;

     @Autowired
     private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

     @Autowired
     private ValidateCodeSecurityConfig validateCodeSecurityConfig;


    //  密码加密
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

       /* ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();*/

         applyPasswordAuthenticationConfig(http);

        http
                .apply(validateCodeSecurityConfig)
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                /*.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/authentication/require")// 表单登录  走到这个接口中进行处理 是页面响应还是json
                .loginProcessingUrl("/authentication/form")   //当是这个请求的url的时候  走/login 的逻辑
                .successHandler(authenticationSuccessHandler)   //登录认证成功调用的handler
                .failureHandler(authenticationFailureHandler)  // 登录失败调用的handler*/
                .and()
                // remember me 的功能的备注
                .rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
                .authorizeRequests()  // 表示下面的是 认证的配置
                // 这个配置里面将验证码的Filter设置在了用户名密码FIlter的前面，另外也排除了/code/image路径的拦截
                .antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",    // code开头全部放过
                        securityProperties.getBrowser().getLoginPage()
                        ).permitAll()  // 如果是这个页面的地址 就通过请求 不然会死循环
                .anyRequest() // 所有的请求
                .authenticated()
                .and()
                .csrf().disable();// 因为SpringSecurity默认会开启csrf安全机制，这里我们先关闭一下
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){

        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
       // jdbcTokenRepository.setCreateTableOnStartup(false);
        return  jdbcTokenRepository;

    }
}
