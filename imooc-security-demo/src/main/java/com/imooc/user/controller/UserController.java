package com.imooc.user.controller;


import com.imooc.user.entity.User;
import com.imooc.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author vicente
 * @since 2020-04-15
 */
@RestController
@RequestMapping("/user")
@Api(value= "用户模块")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getUser")
    @ApiOperation(value = "用户查询服务")
    public User quey(@RequestParam String id){
        return  userService.queryById(id);
    }

    @GetMapping()
    public  User queryResult(){
        return  userService.queryResult();
    }




}
