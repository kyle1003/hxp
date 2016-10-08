package cn.hurry.controller.login;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.hurry.manage.OperateManage;
import cn.hurry.po.operate.Operate;
import cn.hurry.po.user.User;
import cn.hurry.util.StringUtil;

@Controller
public class OperaMenuCoutroller {


	/**
	 * 根据权限获取点击首页按钮后的权限信息
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/to_OperaMenu_page", method = RequestMethod.GET)
	public String to_OperaMenu_page(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		List<Integer> operateArray = (List<Integer>) session.getAttribute("operateList");
		String idstr = request.getParameter("id");
		ArrayList<Operate> newoperaMenuDataList = new ArrayList<Operate>();
		if (!StringUtil.isNullOrEmpty(idstr)) {
			// Operate operate = new Operate();
			// operate.setName("首页");
			// operate.setUrl("test.jsp");
			ArrayList<Operate> operaMenuDataList = OperateManage.getOperaMenuDataListByPid(Integer.parseInt(idstr), operateArray);
			// newoperaMenuDataList.add(operate);
			for (int i = 0; i < operaMenuDataList.size(); i++) {
				Operate op = operaMenuDataList.get(i);
				op.setIndex(i + 1);
				newoperaMenuDataList.add(op);
			}
		}
		request.setAttribute("auto", request.getParameter("auto"));
		request.setAttribute("operaMenuDataList", newoperaMenuDataList);
		return "mainWindow";
	}

	/**
	 * 前往首页
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		List<Integer> operateArray = (List<Integer>) session.getAttribute("operateList");
		ArrayList<Operate> operaMenuDataList = OperateManage.getOperaMenuDataListByPid(-1, operateArray);
		String modules = OperateManage.OperaMenuDataList2String(operaMenuDataList);
		User user = (User) session.getAttribute("user");
		String name = user.getUsername();
		name = user.getUserData() == null ? user.getUsername() : user.getUserData().getName();
		request.setAttribute("name", name);
		request.setAttribute("modules", modules);
		request.setAttribute("operaMenuDataList", operaMenuDataList);
		return "index";
	}


}
