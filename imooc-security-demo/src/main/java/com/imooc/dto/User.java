package com.imooc.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.NotBlank;

public class User {

    public interface UserSimpleView{};
    public  interface  UserDetailView extends  UserSimpleView{};

    private  String id;
    @JsonView(UserSimpleView.class)
    private  String   username;

    @JsonView(UserDetailView.class)
    @NotBlank(message = "密码不能为空")
    private  String password;

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
