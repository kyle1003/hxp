package com.hxp.controller.login;


import com.hxp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;


/**
 * Created by Administrator on 2016/8/20.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
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

    @RequestMapping("/login.do")
    public String load(HttpServletRequest request, ModelMap modelMap){
        String username = request.getParameter("username" );
        String password = request.getParameter("password");

        boolean isExist =userService.isExist(username,password);
       if(isExist){
             modelMap.addAttribute("username",username);
             return "main/main";//登录之后进入的主页面
        }
        return "error/error";//跳转的错误页面
    }

    // JQuery ajax check up/
    @RequestMapping("/check.do")
    public @ResponseBody String check(
            @RequestParam( value = "username",required = true)String username,
            @RequestParam(value = "password",required = true)String password,
             Model model
    ){
        boolean isExist = userService.isExist(username,password);
        String info = new String();
        if (isExist){
            info ="yes";
        }else{
            info="no";
        }
        return  info;
    }
}
