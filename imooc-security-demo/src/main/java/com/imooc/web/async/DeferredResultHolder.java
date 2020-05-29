package com.imooc.web.async;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;
/*
 * @Description
 * 浏览器发起异步请求
   请求到达服务端被挂起
   向浏览器进行响应，分为两种情况：
   3.1 调用DeferredResult.setResult()，请求被唤醒，返回结果
   3.2 超时，返回一个你设定的结果
   浏览得到响应，再次重复1，处理此次响应结果
 * @Date 10:14 2020/4/22
 * @Param
 * @return
 **/
@Component
public class DeferredResultHolder {

     private   Map<String, DeferredResult<String>> map  =  new HashMap<>();

    public Map<String, DeferredResult<String>> getMap() {
        return map;
    }

    public void setMap(Map<String, DeferredResult<String>> map) {
        this.map = map;
    }
}
