package com.imooc.security.core.validate.code;


import com.imooc.security.core.properties.SecurityConstants;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.image.ImageCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private  SecurityProperties securityProperties;

    @Autowired
    private  AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private  ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();




    private Map<String,ValidateCodeType> urlMap= new HashMap<>();


    /**
     * 初始化要拦截的url配置信息
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM,ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getCode().getImage().getUrl(),ValidateCodeType.IMAGE);
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getSms().getUrl(),ValidateCodeType.IMAGE);
    }
    /**
     * 讲系统中配置的需要校验验证码的URL根据校验的类型放入map
     *
     * @param urlString
     * @param type
     */

    protected  void  addUrlToMap(String urlString,ValidateCodeType type){
        if(StringUtils.isNotBlank(urlString)){
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls){
                urlMap.put(url,type);
            }
        }

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ValidateCodeType type = getValidateCodeType(request);
        if(type !=null){
            logger.info("校验请求("+request.getRequestURI()+")中的验证码，验证码类型"+type);
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type).validate(new ServletWebRequest(request,response));
                logger.info("验证通过了");
            }catch ( ValidateCodeException  e){
                authenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }
        // 走下面的逻辑
        filterChain.doFilter(request,response);
    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request
     * @return
     */
    private  ValidateCodeType getValidateCodeType(HttpServletRequest request){
        ValidateCodeType result = null;
        if(!StringUtils.equalsIgnoreCase(request.getMethod(),"get")){
            Set<String> urls = urlMap.keySet();
            for(String url : urls){
                if(pathMatcher.match(url,request.getRequestURI())){
                    result=urlMap.get(url);
                }
            }
        }
        return  result;
    }





    /*旧版的*/
    // @Autowired
   // private SecurityProperties securityProperties ;
   // @Autowired
   /* private AuthenticationFailureHandler authenticationFailureHandler;

    private Set<String> urls=  new HashSet<>();
    private AntPathMatcher pathMatcher = new AntPathMatcher();
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
           boolean action =false;
           // 看配置的路径和 请求的路径  是不是匹配
           for (String url : urls){
               if(pathMatcher.match(url,request.getRequestURI())){
                   action = true;
               }
           }
           if(action){   // 需要验证码的认证
               try {
                  validate(new ServletWebRequest(request));
               }catch (ValidateCodeException e){
                   authenticationFailureHandler.onAuthenticationFailure(request,response,e);
               }
           }
           // 进行下面的跳转的跳转  注意一下
           filterChain.doFilter(request,response);


    }
    @Override
    public  void afterPropertiesSet() throws  ServletException{
         super.afterPropertiesSet();
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(), ",");
        for (String url :configUrls ){
            urls.add(url);
        }
        urls.add("/authentication/form");
    }

    public void validate(ServletWebRequest request) throws ServletRequestBindingException {

        // 获取放在session中的ImageCode
        ImageCode  codeInSession =(ImageCode)sessionStrategy.getAttribute(request,ValidateCodeController.SESSION_KEY);
        // 获取输入框中的用户提交的数据
        String codeInRequest  =ServletRequestUtils.getStringParameter(request.getRequest(),"imageCode");
        if(StringUtils.isBlank(codeInRequest)){
            throw  new ValidateCodeException("验证码不能为空");
        }
        if(null == codeInSession){
            throw  new ValidateCodeException("验证码不存在");
        }
        if(codeInSession.isExpired()){
            sessionStrategy.removeAttribute(request,ValidateCodeController.SESSION_KEY);
            throw  new ValidateCodeException("验证码已经过期");
        }
        if(!StringUtils.equals(codeInSession.getCode(),codeInRequest)){
            sessionStrategy.removeAttribute(request,ValidateCodeController.SESSION_KEY);
            throw  new ValidateCodeException("验证码不匹配");
        }
       // 删除session中的验证码
        sessionStrategy.removeAttribute(request,ValidateCodeController.SESSION_KEY);
    }


    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }

    public AntPathMatcher getPathMatcher() {
        return pathMatcher;
    }

    public void setPathMatcher(AntPathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public SessionStrategy getSessionStrategy() {
        return sessionStrategy;
    }

    public void setSessionStrategy(SessionStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }*/
}
