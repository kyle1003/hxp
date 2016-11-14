package com.hxp.controller.user;

import com.hxp.model.User;
import com.hxp.service.UserService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/11/14.
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("/user")
public class UserController {



    /** @Controller对应表现层的Bean，也就是Action

    注意：如果@Controller不指定其value【@Controller】，则默认的bean名字为这个类的类名首字母小写，如果指定value【@Controller(value=”UserAction”)】或者【@Controller(“UserAction”)】，则使用value作为bean的名字。

       @Scope(“prototype”)表示将Action的范围声明为原型

    可以利用容器的scope=”prototype”来保证每一个请求有一个单独的Action来处理，避免struts中Action的线程安全问题。

    spring 默认scope 是单例模式(scope=”singleton”)，这样只会创建一个Action对象，每次访问都是同一Action对象，数据不安全。

    struts2 是要求每次次访问都对应不同的Action，scope=”prototype” 可以保证当有请求的时候都创建一个Action对象

      @RequestMapping(“/user”)

    RequestMapping是一个用来处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。*/


    @Resource
    private UserService userService;

    /**
     * 跳转登陆页面
     * @return
     */
    @RequestMapping("/toAdminlogin")
    public String toAdminlogin(String referer,HttpServletRequest request){
        request.setAttribute("referer",referer);
        return "login/login";
    }


    /**
     * 账号密码登录
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(User user,Model model) throws Exception {
             user = userService.checkLogin(user.getUsername(),user.getPassword());
        if(user != null){
            model.addAttribute(user);
            return "main/main";//
        }
        return "error/error";
    }
}
