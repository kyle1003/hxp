package cn.hurry.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.hurry.po.user.User;

/**
 * 拦截器
 * 
 * @author zh.sqy@qq.com
 * 
 */
public class ControllerInterceptor implements HandlerInterceptor {

	/**
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 验证 session
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		// 取得登录路径后缀 如controller/login.do
		StringBuffer loginPath = request.getRequestURL();

		String userPath = loginPath.substring(loginPath.lastIndexOf("/") + 1, loginPath.length());
		// 首先判断登录路径
		// 如果是登录路径
		if (userPath.equals("login.do") || userPath.equals("login_authorize.do") || userPath.equals("authorize.do")) {
			return true;
		} else {
			// 判断用户
			if (user != null) {
				return true;
			} else {
				request.getRequestDispatcher("login.do").forward(request, response);
				return false;
			}
		}
	}

	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
