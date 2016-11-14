package com.hxp.service.impl;


import com.hxp.dao.user.UserDao;
import com.hxp.model.User;
import com.hxp.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/9/4.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

   /*登录验证*/
    @Override
    public User checkLogin(String username, String password) {
        //根据用户名实例化用户对象

        User user = userDao.findUserByName(username);
        if(user != null && user.getPassword().equals(password)){
            return user;
        }
        return null;
    }
}
