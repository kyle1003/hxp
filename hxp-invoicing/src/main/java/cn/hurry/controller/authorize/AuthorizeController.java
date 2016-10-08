package cn.hurry.controller.authorize;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.hurry.manage.AuthorizeManager;
import cn.hurry.util.AuthorizeUtil;
import cn.hurry.util.BaseString;
import cn.hurry.util.MacUtil;
import cn.hurry.util.ToPage;

@Controller
public class AuthorizeController {

	/**
	 * 进入授权页面
	 */
	@RequestMapping(value = "/login_authorize", method = RequestMethod.GET)
	public String login_authorize(HttpServletRequest request) {
		String macCode = MacUtil.getMACAddress();
		request.setAttribute("macCode", macCode);
		return "authorize";
	}

	/**
	 * 授权
	 */
	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	public String authorize(HttpServletRequest request) {
		try {
			String authorizeCode = request.getParameter("authorizeCode");
			String partCode = AuthorizeUtil.getPartAuthorizeCode(authorizeCode);
			String macCode = MacUtil.getMACAddress();
			request.setAttribute("macCode", macCode);
			if (!AuthorizeUtil.isLocalAuthorizeCode(partCode)) {
				request.setAttribute(BaseString.KEY_ERROR, "授权码错误，请重新输入！");
				return "authorize";
			}

			if (AuthorizeUtil.isAuthorizeCodeExpired(partCode)) {
				request.setAttribute(BaseString.KEY_ERROR, "授权码已过期，请重新输入！");
				return "authorize";
			}

			AuthorizeManager.addAuthorizeCode(authorizeCode);

			request.setAttribute(BaseString.KEY_MESSAGE, "授权成功！授权截止日期：" + AuthorizeUtil.getAuthorizeCodeExpiredDate(partCode));
			return "authorize";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(BaseString.KEY_EXCEPTION, e);
			return ToPage.HT_INFO;
		}
	}
}
