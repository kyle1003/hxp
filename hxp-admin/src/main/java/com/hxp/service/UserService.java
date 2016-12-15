package com.hxp.service;


import com.hxp.model.User;

/**
 * Created by Administrator on 2016/9/4.
 */

public interface UserService {

    //通过用户名及密码核查用户登录
    public boolean isExist(String username, String password);
}
