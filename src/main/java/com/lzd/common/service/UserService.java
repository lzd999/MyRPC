package com.lzd.common.service;

import com.lzd.common.pojo.User;

/**
 * @author 李泽栋
 */
public interface UserService {
    Boolean insertUser(User user);

    User getUserById(Integer id);
}
