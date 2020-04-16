package com.imooc.user.service.impl;

import com.imooc.user.entity.User;
import com.imooc.user.mapper.UserMapper;
import com.imooc.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author vicente
 * @since 2020-04-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private  UserMapper userMapper;

    @Override
    public User queryById(String id) {
        return  userMapper.queryUser(id);
    }

    @Override
    public User queryResult() {
        return userMapper.queryResult();
    }
}
