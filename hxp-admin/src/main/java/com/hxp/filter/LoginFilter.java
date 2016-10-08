package com.hxp.filter;

import com.hxp.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/4.
 */
public class LoginFilter implements Filter {

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        User user = (User) req.getSession().getAttribute("user");
        String url = req.getRequestURI();
        boolean flg = true;
        //判断后台用户是否可以通过登录路径登录程序  listMenu
        if (user != null) {
            Map map = req.getParameterMap();
            url = initReferer(map, url);
            //TODO
            //这个是做什么的URLDecoser
            url = URLDecoder.decode(url, "utf8");
            String ur = url.substring(url.length() - 1);
            if (ur.equals("&")) {
                url = url.substring(0, url.length() - 1);
            }
//            boolean menuAll=findMenuAll(url, req);
//            if(menuAll){
//                boolean result=findMenuList(url,req);
//                if(result==false){
//                    req.getSession().removeAttribute("sysuser");
//                    req.getSession().invalidate();
//                    user=null;
//                    flg=false;
//                }
//            }
        }
        if (url.startsWith("/login/toAdminlogin.mxd") || url.startsWith("/login/tologin.mxd") || url.startsWith("/login/login.mxd") || url.startsWith("/login/captcha.mxd") || url.startsWith("/login/captcha.mxd") || user != null) {
            chain.doFilter(request, response);
        } else {
            String path = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
            Map map = req.getParameterMap();
            url = initReferer(map, url);
            if (flg) {
                res.sendRedirect(path + "/login/toAdminlogin.mxd?referer=" + url);
            } else {
                res.sendRedirect(path + "/login/toAdminlogin.mxd");
            }
        }
        //登录超时
    }

    private String initReferer(Map map, String url) {
        if(map != null && !map.isEmpty()){
            url += "?" ;
            for(Object obj : map.keySet()){
                url += (obj + "=");
                if(map.get(obj) != null){
                    url += ((String[])map.get(obj))[0];
                }
                url += "&";
            }
        }
        try {
            url = URLEncoder.encode(url,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File and Code Templates | Code | Catch Statement Body.
        }
        return url;
    }
}
