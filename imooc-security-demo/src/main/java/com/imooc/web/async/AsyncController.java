package com.imooc.web.async;


import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class AsyncController {

  @Autowired
  private  MockQueue mockQueue;

  @Autowired
  private DeferredResultHolder deferredResultHolder;

  private Logger logger  = LoggerFactory.getLogger(getClass());

  @GetMapping("/order")
  public DeferredResult<String> order(){

      logger.info("主线程开始");

      String orderNum = RandomStringUtils.randomNumeric(8);

      mockQueue.setPlaceOrder(orderNum);

      DeferredResult<String> result = new DeferredResult<>();
      // 将orderNum为key 的请求设置到deferredResultHolder 中
      deferredResultHolder.getMap().put(orderNum,result);
      return  result;
  }





}
