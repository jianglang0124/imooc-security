
package com.imooc.web.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.dto.User;
import com.imooc.dto.UserQueryCondition;
import com.imooc.exception.UserIsNotExistException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/userInfo")
@Api(value= "用户模块")
public class UserTestController {


    @PutMapping("/{id:\\d+}")
    public  User update(@Valid @RequestBody  User user,BindingResult errors ){

        if(errors.hasErrors()){
            errors.getAllErrors().stream().forEach(error -> {
              FieldError fieldError= (FieldError)error;
                System.out.println(fieldError.getDefaultMessage());
            });
        }


        System.out.println(user.getId());
        System.out.println(user.getPassword());
        System.out.println(user.getUsername());
        user.setId("1");
        return  user;
    }

    @DeleteMapping("/{id:\\d+}")
    public void  delete(@PathVariable String id){
        System.out.println(id);
    }

    @PostMapping
    public  User create(@Valid  @RequestBody  User user){
       // 打印数据校验的出现异常的信息 这样的话 请求是可以进入接口的
        /*if(errors.hasErrors()){
            errors.getAllErrors().stream().forEach(error-> {
                FieldError fieldError  = (FieldError) error;
                String  message= fieldError.getField()+fieldError.getDefaultMessage();
                System.out.println(message);
            });

        }*/
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        user.setId("1");
        return  user;
    }

    @GetMapping
    @JsonView(User.UserSimpleView.class)
    @ApiOperation(value = "用户查询服务")
    public List<User>  query(UserQueryCondition userQueryCondition, @PageableDefault(page = 2,size = 17,sort = "username,asc") Pageable pageable){
        System.out.println(ReflectionToStringBuilder.toString(userQueryCondition, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getSort());
       List<User> users = new ArrayList<>();
       users.add(new User());
       users.add(new User());
       users.add(new User());
       return  users;
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getInfo(@PathVariable  String id){
         //  throw  new UserIsNotExistException(id);
         System.out.println("进入userInfo服务");
         User user = new User();
         user.setUsername("Tom");
         user.setPassword("123");
         return  user;
    }

}
