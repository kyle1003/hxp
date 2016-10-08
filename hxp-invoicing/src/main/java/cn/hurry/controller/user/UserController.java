package cn.hurry.controller.user;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.hurry.po.operate.ManagerGroup;
import cn.hurry.po.operate.Operate;
import cn.hurry.po.user.User;
import cn.hurry.po.user.UserData;
import cn.hurry.service.operate.ManagerGroupService;
import cn.hurry.service.user.UserService;
import cn.hurry.util.BaseString;
import cn.hurry.util.JSON;
import cn.hurry.util.MD5;
import cn.hurry.util.Pageutil;
import cn.hurry.util.ToPage;

/**
 * 用户类控制器
 * 
 */
@Controller
public class UserController {

	/**
	 * 串行标示
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户业务类
	 */
	@Resource
	private UserService userService;

	/**
	 * 管理组业务类
	 */
	@Resource
	private ManagerGroupService managerGroupService;

	/**
	 * 进入添加用户页面
	 */
	@RequestMapping(value = "/user_doAdd", method = RequestMethod.GET)
	public String user_doAdd() {
		return "ht/operate/add/adduser";
	}

	@RequestMapping(value = "/user_combobox", method = RequestMethod.GET)
	public String user_combobox(HttpServletResponse response, Pageutil pageUtil) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("isAdminNot", true);
			List<User> list = userService.getUser(map);
			// 为null时默认为table
			String json = JSON.Objects2Json(list, JSON.COMBOBOX_ID,new String[]{"text","username"});
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 加载用户数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user_doSelect", method = RequestMethod.POST)
	public String user_doSelect(HttpServletResponse response, Pageutil pageUtil) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			if ("".equals(pageUtil.getKey())) {
				pageUtil.setKey(null);
			}
			// 分页数据
			int index = pageUtil.getPageIndex();
			int size = pageUtil.getPageSize();
			// 字段排序
			String sortField = pageUtil.getSortField();
			String sortOrder = pageUtil.getSortOrder();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("key", pageUtil.getKey());
			map.put("sortField", sortField != null ? sortField.replaceAll("#", ".") : null);
			map.put("sortOrder", sortOrder);
			map.put("isAdminNot", true);
			int dataSize = userService.getUserCount(map);
			int firstResult = pageUtil.getStart();
			// 当前页没有数据后跳转到前一页
			if (pageUtil.getStart() >= dataSize && index != 0) {
				firstResult = (index - 1) * size;
			}
			map.put("maxResults", size);
			map.put("firstResult", firstResult);
			ArrayList list = userService.getUser(map);
			// 将数据json化
			String json = JSON.objects2JsonByType(list, dataSize, JSON.TYPE_TABLE);
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 编辑用户
	 */
	@RequestMapping(value = "/user_doSave", method = RequestMethod.POST)
	public String doSave(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		String message = "";
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			if (hasOperate(request)) {
				// ======================业务逻辑====================
				String json = request.getParameter("json");
				String hasDate = request.getParameter("hasData");
				User user = (User) JSON.Json2Object(json, User.class);
				UserData userData = (UserData) JSON.Json2Object(json, UserData.class);
				if(hasDate.equals("true")){
					user.setUserData(userData);
				}
				userService.insertUser(user);
				message = BaseString.INFO_ADD_SUCCESS;
				// ======================业务逻辑====================
			} else {
				message = BaseString.INFO_NO_OPERATE;
			}
		} catch (Exception e) {
			message = e.getMessage();
			e.printStackTrace();
		}
		out.print(message);
		out.close();
		return null;
	}

	/**
	 * 进入更新用户信息页面
	 */
	@RequestMapping(value = "/user_toUpdate", method = RequestMethod.GET)
	public String user_toUpdate_doGet(HttpServletRequest request, User user, Model model) {
		try {
			if (hasOperate(request)) {
				// ======================业务逻辑====================
				// 根据用户id获取用户
				user = userService.getUserById(Integer.parseInt(request.getParameter("id")));
				model.addAttribute("user", user);
				// 获取所有权限组
				List<ManagerGroup> managerGroupList = managerGroupService.selectAllManagerGroup();
				model.addAttribute("managerGroupList", managerGroupList);
				return "jsp/operate/update/user";
				// ======================业务逻辑====================
			} else {
				request.setAttribute(BaseString.KEY_ERROR, BaseString.INFO_NO_OPERATE);
				return ToPage.HT_INFO;
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(BaseString.KEY_EXCEPTION, e);
			return ToPage.HT_INFO;
		}
	}

	/**
	 * 更新用户信息
	 */
	@RequestMapping(value = "/user_doUpdate", method = RequestMethod.POST)
	public String user_toUpdate_doPost(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		String message = "";
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			if (hasOperate(request)) {
				// ======================业务逻辑====================
				String json = request.getParameter("json");
				String hasDate = request.getParameter("hasData");
				User user = (User) JSON.Json2Object(json, User.class);
				UserData userData = (UserData) JSON.Json2Object(json, UserData.class);
				if(hasDate.equals("true")){
					user.setUserData(userData);
				}
				userService.updateUser(user);
				message = BaseString.INFO_UPDATE_SUCCESS;
				// ======================业务逻辑====================
			} else {
				message = BaseString.INFO_NO_OPERATE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
		}
		out.print(message);
		out.close();
		return null;
	}

	/**
	 * 修改密码
	 */
	@RequestMapping(value = "/doUpdatePassword", method = RequestMethod.POST)
	public String doUpdatePassword(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		PrintWriter out = null;
		String message = "";
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			if (hasOperate(request)) {
				if (!password.equals(confirmPassword)) {
					message = "两次输入密码不相同!";
				} else {
					User user = userService.getUserById(Integer.parseInt(id));
					user.setPassword(MD5.encode(confirmPassword));
					userService.updateUser(user);
					message = "修改密码成功!";
				}
			} else {
				message = BaseString.INFO_NO_OPERATE;
			}
		} catch (Exception e) {
			message = e.getMessage();
		}
		out.print(message);
		return null;
	}

	/**
	 * 删除用户
	 */
	@RequestMapping(value = "/user_doDelete", method = RequestMethod.GET)
	public String doDelete(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			if (hasOperate(request)) {
				// ======================业务逻辑====================
				int id = Integer.parseInt(request.getParameter("id"));
				User oldUser = userService.getUserById(id);
				if (oldUser.isAdmin()) {
					out.print("该用户拥有最高级权限无法删除!");
				} else {
					userService.deleteUser(id);
					out.print("删除成功");
				}
				// ======================业务逻辑====================
			} else {
				out.print("删除失败,权限不足");
			}
		} catch (Exception e) {
			out.print(e.getMessage());
		}
		return null;
	}

	/**
	 * 是否拥有权限
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean hasOperate(HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Integer> operateArray = (List<Integer>) session.getAttribute("operateList");
		if (operateArray.contains(Operate.USER_MANAGE)) {
			return true;
		} else {
			return false;
		}
	}

}
