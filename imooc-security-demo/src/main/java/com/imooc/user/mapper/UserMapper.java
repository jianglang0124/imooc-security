package com.imooc.user.mapper;

import com.imooc.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author vicente
 * @since 2020-04-15
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


    User queryUser(@Param("id") String id);

    User queryResult();
}
