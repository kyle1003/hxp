package com.hxp.dao.user;

import com.hxp.model.User;

/**
 * Created by Administrator on 2016/11/14.
 */
public interface UserDao {
    //接口方法，通过用户名得到User对象

    public User findUserByName(String username);
}
