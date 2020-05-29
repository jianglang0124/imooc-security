package com.imooc.validator;

import com.imooc.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
/*
 * @Description MyConstraintValidator  自己定义的校验器  要实现ConstraintValidator 的方法 并且将自己定义的注解的名称传递过来
 * @Date 14:06 2020/4/20
 * @Param
 * @return
 **/
public class MyConstraintValidator  implements ConstraintValidator<MyConstraint,Object> {

    @Autowired
    private HelloService helloService;

    @Override
    public void initialize(MyConstraint constraintAnnotation) {
        System.out.println("start  init");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        System.out.println(value);
        helloService.Hello("world");
        // 这个是看校验成功还是失败的
        return false;
    }
}
