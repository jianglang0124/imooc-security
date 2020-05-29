package com.imooc.security.core.validate.code.impl;

import com.imooc.security.core.validate.code.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;


public abstract  class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {


    // 操作session 的工具类
     private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

     // spring 开发的一个技巧  叫做 依赖搜索
     @Autowired
    Map<String, ValidateCodeGenerator> validateCodeGenerators;


     // 主干的逻辑  生成  保存发送
    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode =gengerate(request);
        save(request,validateCode);
        send(request,validateCode);
    }

    private  C gengerate(ServletWebRequest request){
        String type  = getValidateCodeType(request).toString().toLowerCase();
        String  generatorName =type+ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if(null == validateCodeGenerator){
            throw  new ValidateCodeException("验证码生成器"+generatorName+"不存在");
        }
        return  (C) validateCodeGenerator.gengerate(request);
    }


    /**
     * 根据请求的url获取校验码的类型
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request){
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return  ValidateCodeType.valueOf(type.toUpperCase());
    }

    /**
     * 保存校验码
     */
   private void save(ServletWebRequest request,ValidateCode validateCode){
       sessionStrategy.setAttribute(request,getSessionKey(request),validateCode);
    }

    /**
     * 构建验证码放入session时的key
     */
    private  String getSessionKey(ServletWebRequest request){
          return  SESSION_KEY_PREFIX+getValidateCodeType(request).toString().toLowerCase();
    }

    /**
     * 发送校验码，由子类实现
     */
    protected  abstract  void  send(ServletWebRequest request,C validateCode) throws  Exception;

    @Override
    public void validate(ServletWebRequest request) {

        ValidateCodeType processorType = getValidateCodeType(request);
        String sessionKey = getSessionKey(request);

        C codeInSession = (C) sessionStrategy.getAttribute(request, sessionKey);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(processorType + "验证码不存在");
        }

        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(request, sessionKey);
            throw new ValidateCodeException(processorType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, sessionKey);
    }


}
