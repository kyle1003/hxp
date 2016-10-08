package cn.hurry.controller.operate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hurry.manage.OperateManage;
import cn.hurry.po.operate.Operate;
import cn.hurry.po.user.User;
import cn.hurry.util.ToPage;
import cn.hurry.util.JSON;
import cn.hurry.util.MapUtil;

/**
 * 登录类控制器
 * 
 */
@Controller
public class OperateController {

	private static final long serialVersionUID = 1L;

	/**
	 * 获取用户权限列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user_operate_list_json")
	public String user_operate_list_json(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			List<Integer> list = (List<Integer>) session.getAttribute("operateList");
			List<Operate> operates = new ArrayList<Operate>();
			for (int i : list) {
				operates.add(OperateManage.getOperaMenuDataListById(i));
			}
			response.getWriter().print(OperateManage.OperaMenuDataList2Combobox(operates));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("exceptions", e);
			return ToPage.HT_INFO;
		}
		return null;
	}

	/**
	 * 加载权限列表数据(表格)
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/operate_doSelect")
	public String operate_doSelect(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			if (hasOperate(request)) {
				// ======================业务逻辑====================
				List<Operate> list = OperateManage.getOperaMenuDataList();
				HashMap result = new HashMap();
				result.put("data", MapUtil.fomatData(list));
				result.put("total", list.size());
				// 将数据json化
				String json = JSON.Encode(result);
				response.getWriter().write(json);
				// ======================业务逻辑====================
			} else {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加载权限列表数据(树)
	 */
	@RequestMapping(value = "/operate_data_tree")
	public String operate_data_tree(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			if (hasOperate(request)) {
				// ======================业务逻辑====================
				List<Operate> list = OperateManage.getOperaMenuDataList();
				response.getWriter().write(OperateManage.OperaMenuDataList2Combobox(list));
				// ======================业务逻辑====================
			} else {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public User getUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
	}

	/**
	 * 是否拥有权限
	 * 
	 * @param request
	 * @return
	 */
	public boolean hasOperate(HttpServletRequest request) {
		// HttpSession session = request.getSession();
		// List<Integer> operateArray = (List<Integer>) session.getAttribute("operateList");
		return true;
	}
}
