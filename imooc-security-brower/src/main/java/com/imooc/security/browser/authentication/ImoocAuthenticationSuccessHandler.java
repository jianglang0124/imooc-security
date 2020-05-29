package com.imooc.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.security.core.properties.LoginResponseType;
import com.imooc.security.core.properties.SecurityProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
 * @Description
 * SpringSecurity提供了一个AuthenticationSuccessHandler接口，当用户认证成功后，
 * 会调用这个接口中唯一的方法onAuthenticationSuccess，
 * 所以我们只要重写这个方法就可以自定义我们登录成功的逻辑
 * @Date 10:47 2020/5/21
 * @Param
 * @return
 **/
@Component("ImoocAuthenticationSuccessHandler")
public class ImoocAuthenticationSuccessHandler  extends SavedRequestAwareAuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
        logger.info("登录成功");

        if(LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())){
            response.setContentType("application/json;charset=UTF-8");
            // 讲认证的信息写入到response 中
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        }else {
            // 调用父类的方法进行跳转
            super.onAuthenticationSuccess(request,response,authentication);
        }

    }
}
