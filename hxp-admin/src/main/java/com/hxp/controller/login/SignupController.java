package com.hxp.controller.login;


import com.hxp.model.User;
import com.hxp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * Created by Administrator on 2016/8/20.
 */
@Controller
@RequestMapping("/login")
public class SignupController {

    @Autowired
    private UserService userService;
    /**
     * 跳转登陆页面
     * @return
     */
    @RequestMapping("/toAdminlogin")
    public String toAdminlogin(String referer,HttpServletRequest request){
        request.setAttribute("referer",referer);
        return "login/signup";
    }

    @RequestMapping("/signup.do")
    public String load(HttpServletRequest request, ModelMap modelMap) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        if (session.getAttribute("user") != null && session.getAttribute("user").equals(username)) {//防止多次刷新重复提交
            return "main/main";
        } else {
            User user = new User();
            user.setPassword(password);
            user.setUsername(username);
        }
        return "main/main";
    }
}
