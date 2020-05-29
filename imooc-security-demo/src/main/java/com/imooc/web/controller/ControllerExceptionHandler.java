package com.imooc.web.controller;


import com.imooc.exception.UserIsNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
/*
 * @Description    @RestControllerAdvice  所有的controller 的 异常都会在这边得到处理
 * @Date 15:31 2020/4/20
 * @Param
 * @return
 **/
@RestControllerAdvice
public class ControllerExceptionHandler {


    // 处理的是UserIsNotExistException 这个异常 以json 的格式返回 返回的状态指定是服务器的状态错误
    @ExceptionHandler(UserIsNotExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> handleUserNotExistException(UserIsNotExistException ex){
         HashMap map = new HashMap<>();
         map.put("id",ex.getId());
         map.put("message",ex.getMessage());
         return  map;
    }


}
