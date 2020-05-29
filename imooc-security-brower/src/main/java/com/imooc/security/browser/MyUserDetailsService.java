package com.imooc.security.browser;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
/*
 * @Description
 *   UserDetailsService   处理用户信息的获取逻辑
 *   UserDetails  处理用户校验逻辑
 *   PasswordEncoder  处理的密码加密解密
 * @Date 20:09 2020/5/6
 * @Param
 * @return
 **/
@Component
public class MyUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("登录的用户名："+username);
        // 根据用户名查找用户信息

        // 将 用户表单的密码和下面的密码比较  一致就是通过了
        String password = passwordEncoder.encode("123456");
        logger.info("密码是："+password);
        // 根据查找到的用户判断用户是否被冻结
        return
                // new User(username,"123456", AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
                 new User(username,password,true,true,true,true,AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
