package com.imooc.web.async;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
/*
 * @Description 监听器  Spring 启动的时候就会去监听
 * @Date 10:27 2020/4/22
 * @Param 
 * @return 
 **/
@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {


    @Autowired
    MockQueue mockQueue;
    @Autowired
    DeferredResultHolder deferredResultHolder;

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        // 起一个线程进行监听 这样的话 主线程就不会堵塞
        new Thread(()->{
            while (true){
                if(StringUtils.isNotBlank(mockQueue.getComplateOrder())){
                    String  orderNum = mockQueue.getComplateOrder();
                    logger.info("返回订单的处理的结果"+orderNum);
                    deferredResultHolder.getMap().get(orderNum).setResult("place order success");
                    mockQueue.setComplateOrder(null);
                }else{
                    try {
                        Thread.sleep(100);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }
}
