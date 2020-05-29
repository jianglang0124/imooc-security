package com.imooc.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.validator.MyConstraint;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
import java.util.Date;

public class User {

    public interface UserSimpleView{};
    public  interface  UserDetailView extends  UserSimpleView{};

    private  String id;

    @MyConstraint(message = "这是一个自定义的校验")
    private  String   username;

    @JsonView(UserDetailView.class)
    @NotBlank(message = "密码不能为空")
    private  String password;

/*
    @Past
    private Date birthDay;
    public Date getBirthDay() {
        return birthDay;
    }
    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }*/


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
