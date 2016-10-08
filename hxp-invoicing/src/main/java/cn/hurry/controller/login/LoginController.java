package cn.hurry.controller.login;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.hurry.core.SessionContainer;
import cn.hurry.manage.AuthorizeManager;
import cn.hurry.manage.OperateManage;
import cn.hurry.po.operate.ManagerGroup;
import cn.hurry.po.operate.Operate;
import cn.hurry.po.user.User;
import cn.hurry.service.operate.ManagerGroupService;
import cn.hurry.service.operate.OperateService;
import cn.hurry.service.user.UserService;
import cn.hurry.util.AuthorizeUtil;
import cn.hurry.util.BaseString;
import cn.hurry.util.MD5;
import cn.hurry.util.ToPage;

/**
 * 登录类控制器
 * 
 * @author zh.sqy@qq.com
 * 
 */
@Controller
public class LoginController {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户业务类
	 */
	@Resource
	private UserService userService;

	/**
	 * 管理组的业务类
	 */
	@Resource
	private ManagerGroupService managerGroupService;

	/**
	 * 权限业务类
	 */
	@Resource
	private OperateService operateService;

	/**
	 * Get方式请求 login.do的时候直接跳转
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGet() {
		return ToPage.HT_LOGIN;
	}
	
	/**
	 * Post方式请求 login.do 进行用户名和密码验证
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPost(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			// 获得前台输入的验证码
			String vercode = request.getParameter("vercode");
			// 取出系统随机生成的验证码
			String ver2 = (String) session.getAttribute("rand");
			// 验证数字验证码（已废弃 现使用英文+数字验证码）
			// if (!StringUtil.isNumber(vercode)) {
			// request.setAttribute(BaseString.KEY_ERROR, "验证码错误!");
			// return ToPage.HT_LOGIN;
			// }
			if (username == null || "".equals(username)) {
				request.setAttribute(BaseString.KEY_ERROR, "用户名不能为空");
				return ToPage.HT_LOGIN;
			}
			if (password == null || "".equals(password)) {
				request.setAttribute(BaseString.KEY_ERROR, "密码不能为空");
				return ToPage.HT_LOGIN;
			}
			// 强制系统的随机生成验证码失效
			session.setAttribute("rand", null);
			// 如果用户输入的验证码和系统的验证码相同
			if (!vercode.equalsIgnoreCase(ver2)) {
				request.setAttribute(BaseString.KEY_ERROR, "验证码不匹配，请重新输入！");
				return ToPage.HT_LOGIN;
			}

			User user = userService.getUserByUsername(username);
			if (user == null) {
				request.setAttribute(BaseString.KEY_ERROR, "不存在的用户！");
				return ToPage.HT_LOGIN;
			}

			if (!user.getPassword().equals(MD5.encode(password))) {
				request.setAttribute(BaseString.KEY_ERROR, "用户名/密码不匹配，请重新输入！");
				return ToPage.HT_LOGIN;
			}

			if (!user.isValid()) {
				request.setAttribute(BaseString.KEY_ERROR, "账户已失效！");
				return ToPage.HT_LOGIN;
			}

			// 相同账号登陆，把已登陆账号踢下线
			SessionContainer sc = SessionContainer.getInstance();
			HttpSession loginedUserSession = sc.getSessionByUser(user.getId());
			if (loginedUserSession != null) {
				sc.delSession(loginedUserSession);
				loginedUserSession.invalidate();
			}
			session.setAttribute("user", user);
			// 如果账户是超级管理员则，设置其拥有所有权限，否则获取该账户的真实权限,并放入session中
			List<Integer> operateArray = new ArrayList<Integer>();
			if (user.isAdmin()) {
				List<Operate> operateList = OperateManage.getOperaMenuDataList();
				for (int i = 0; i < operateList.size(); i++) {
					operateArray.add(((Operate) operateList.get(i)).getId());
				}
			} else {
				ManagerGroup groupmanager = managerGroupService.selectManagerGroupById(user.getManagerGroupId());
				if (groupmanager != null) {
					List<Operate> operateList = operateService.selectOperateByManagerGroupeId(groupmanager.getId());
					for (int i = 0; i < operateList.size(); i++) {
						operateArray.add(((Operate) operateList.get(i)).getId());
					}
				}
			}
			session.setAttribute("operateList", operateArray);
			return "redirect:index.do";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("exceptions", e);
			return ToPage.HT_INFO;
		}
	}

}
