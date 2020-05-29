package com.imooc.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/*
 * @Description  自己定义的注解
 * @Date 14:06 2020/4/20
 * @Param  MyConstraintValidator 自己定义的校验器
 * @return
 **/
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyConstraintValidator.class)
public  @interface MyConstraint {

    String message();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
