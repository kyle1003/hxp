package cn.hurry.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hurry.util.ToPage;

/**
 * 用户注销控制器
 * 
 * @author zh.sqy@qq.com
 * 
 */
@Controller
public class EnableSessionController {

	/**
	 * 串行版本标识
	 */
	private static final long serialVersionUID = 1L;

	@RequestMapping(value = "/enableSession")
	public String enableSession(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().invalidate();
		return ToPage.HT_LOGIN;
	}
}
