package com.hxp.service.impl;

import com.hxp.dao.mapping.user.UserMapper;
import com.hxp.model.User;
import com.hxp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/9/4.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public  User findUserByUsername(User user) {

        return userMapper.findUserByUsername(user);
    }
}
