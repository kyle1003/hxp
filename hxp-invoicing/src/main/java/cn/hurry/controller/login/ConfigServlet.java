package cn.hurry.controller.login;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import cn.hurry.manage.AuthorizeManager;

/**
 * 初始化设置servlet
 * 
 * @author zh.sqy@qq.com
 * 
 */
public class ConfigServlet extends HttpServlet {

	/**
	 * 串行标识
	 */
	private static final long serialVersionUID = -6854675464999412559L;

	public ConfigServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		new AuthorizeManager();
	}

}
