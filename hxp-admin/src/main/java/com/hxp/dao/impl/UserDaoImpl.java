package com.hxp.dao.impl;

import com.hxp.dao.mapping.user.UserMapper;
import com.hxp.dao.user.UserDao;
import com.hxp.model.User;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;


/**
 * Created by Administrator on 2016/11/14.
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {

/**   @Repository对应数据访问层Bean
   @Repository(value=”userDao”)注解是告诉Spring，让Spring创建一个名字叫“userDao”的UserDaoImpl实例。
   当Service需要使用Spring创建的名为“userDao”的UserDaoImpl实例时，
   就可以使用@Resource(name =”userDao”)注解告诉Spring，Spring把创建好的userDao注入给Service即可。*/


    //注解引用Mapper资源
    @Resource(name ="userMapper")
    private UserMapper userMapper;

    /* 根据用户名查找用户对象*/
    public User findUserByName(String username){
        //调用Mapper类从数据库中得到user对象
        return  userMapper.getUserByName(username);
    }
}
