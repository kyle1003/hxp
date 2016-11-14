package com.hxp.controller.login;


import com.hxp.model.Result;
import com.hxp.model.User;
import com.hxp.service.UserService;
import com.hxp.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * Created by Administrator on 2016/8/20.
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    /**
     * 跳转登陆页面
     * @return
     */
    @RequestMapping("/toAdminlogin")
    public String toAdminlogin(String referer,HttpServletRequest request){
        request.setAttribute("referer",referer);
        return "login/login";
    }
}
