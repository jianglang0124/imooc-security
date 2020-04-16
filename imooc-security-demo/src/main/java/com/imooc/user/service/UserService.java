package com.imooc.user.service;

import com.imooc.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author vicente
 * @since 2020-04-15
 */
public interface UserService extends IService<User> {

    User  queryById(String id);

    User queryResult();

}
