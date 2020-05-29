package com.imooc.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
 * @Description  模拟 其他服务的对实际请求的处理
 * @Date 10:30 2020/4/22
 * @Param
 * @return
 **/

@Component
public class MockQueue {

    private  String placeOrder;

    private  String complateOrder;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder) {

        new Thread(()->{
            logger.info("接收到下单的请求："+placeOrder);
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            this.complateOrder = placeOrder;
            logger.info("下单的处理结果完毕");
        }).start();
    }

    public String getComplateOrder() {
        return complateOrder;
    }

    public void setComplateOrder(String complateOrder) {
        this.complateOrder = complateOrder;
    }
}
