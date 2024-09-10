package com.lzd.common.service.impl;

import com.lzd.common.pojo.User;
import com.lzd.common.service.UserService;

/**
 * @author 李泽栋
 */
public class UserServiceImpl implements UserService {
    @Override
    public Boolean insertUser(User user) {
        // 模拟插入用户
        System.out.println("用户插入成功，用户id：" + user.getId());
        return Boolean.TRUE;
    }

    @Override
    public User getUserById(Integer id) {
        // 模拟查询用户
        System.out.println("查询用户成功，用户id：" + id);
        return new User(id, "lzd", 25, "男");
    }
}
