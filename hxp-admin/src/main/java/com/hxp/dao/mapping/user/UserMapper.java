package com.hxp.dao.mapping.user;

import com.hxp.model.User;

/**
 * Created by Administrator on 2016/7/29.
 */
public interface UserMapper  {

    /**
     * 根据用户名查询用户
     * @return 用户
     */
    public User getUserByName(String username);
}
