package com.hxp.service;


/**
 * Created by Administrator on 2016/9/4.
 */

public interface UserService {

    //通过用户名及密码核查用户登录
    public boolean checkLogin(String username,String password);
}
