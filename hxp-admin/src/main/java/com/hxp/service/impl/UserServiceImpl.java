package com.hxp.service.impl;


import com.hxp.dao.UserDao;
import com.hxp.model.User;
import com.hxp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/9/4.
 */



/** @Service对应的是业务层Bean

这样当Action需要使用UserServiceImpl的的实例时,就可以由Spring创建好的”userService”注入给Action：在Action只需要声明一个名字叫“userService”的变量来接收由Spring注入的”userService”即可*/
/**@Service("UserService") 注解用于标示此类为业务层组件,在使用时会被注解的类会自动由spring进行注入,无需我们创建实例*/
@Service("UserService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

   /*登录验证*/
    public boolean checkLogin(String username, String password) {
        //根据用户名实例化用户对象
        User user = userDao.findUserByName(username);
        if(user != null && user.getPassword().equals(password)){
            return true;
        }
        return false;
    }
}
