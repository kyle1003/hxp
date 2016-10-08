package com.hxp.service;

import com.hxp.model.User;

/**
 * Created by Administrator on 2016/9/4.
 */
public interface UserService {

    public User findUserByUsername(User user) throws Exception;
}
