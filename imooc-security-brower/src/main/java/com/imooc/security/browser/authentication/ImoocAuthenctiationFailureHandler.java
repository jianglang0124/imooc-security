package com.imooc.security.browser.authentication;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.security.browser.support.SimpleResponse;
import com.imooc.security.core.properties.LoginResponseType;
import com.imooc.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @Description
 * 用户认证失败时也有一个类似AuthenticationSuccessHandler的Handler，它就是AuthenticationFailureHandler
 * 自定义是失败的Handler
 * @Date 11:01 2020/5/21
 * @Param
 * @return
 **/
@Component("ImoocAuthenctiationFailureHandler")
public class ImoocAuthenctiationFailureHandler  extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger  = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private ObjectMapper objectMapper;


    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        logger.info("登录失败");


        if(LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            // 返回的异常简单点
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
        }else {
            super.onAuthenticationFailure(request,response,exception);
        }


    }
}
