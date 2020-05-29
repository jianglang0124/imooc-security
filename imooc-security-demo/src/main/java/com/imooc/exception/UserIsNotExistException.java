package com.imooc.exception;
/*
 * @Description 自定义的异常处理的方式
 * @Date 16:54 2020/4/20
 * @Param
 * @return
 **/
public class UserIsNotExistException extends  RuntimeException{

    private  String id ;

    public UserIsNotExistException(String id){
        super("User is not exists");
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
