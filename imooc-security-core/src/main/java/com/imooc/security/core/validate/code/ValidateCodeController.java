package com.imooc.security.core.validate.code;


import com.imooc.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * @Description
 *  我们的html页面在初始化的时候必须访问Controller来生成图形验证码，
 *  此时我们需要提供一个Controller来完成这段逻辑，
 *  Controller会用到ValidateCodeGenerator来生成验证码
 * @Date 17:31 2020/5/20
 * @Param
 * @return
 **/
@RestController
public class ValidateCodeController {

   public  static  final  String SESSION_KEY="SESSION_KEY_IMAGE_CODE";

    /*private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private ValidateCodeGenerator codeGenerator;

    @GetMapping("/code/image")
    public  void  createCode(HttpServletRequest request , HttpServletResponse response) throws IOException {
        ImageCode imageCode = codeGenerator.gengeate(new ServletWebRequest(request));
        // 将生成的二维码放入session中
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,imageCode);
        ImageIO.write(imageCode.getBufferedImage(),"JPEG",response.getOutputStream());

    }*/

  // 重构
    @Autowired
    private  ValidateCodeProcessorHolder validateCodeProcessorHolder;


    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/{type}")
    public void  create(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws  Exception{
        validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request,response));
    }

}
