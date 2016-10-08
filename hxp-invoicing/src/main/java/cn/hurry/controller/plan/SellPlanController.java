package cn.hurry.controller.plan;

import java.io.PrintWriter;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.hurry.po.operate.Operate;
import cn.hurry.service.plan.SellPlanService;
import cn.hurry.util.BaseString;
import cn.hurry.util.ToPage;

/**
 * 销售方案控制器
 * 
 */
@Controller
public class SellPlanController {

	private static final long serialVersionUID = 1L;

	@Resource
	private SellPlanService sellPlanService;

	@RequestMapping(value = "/sellPlan_update", method = RequestMethod.POST)
	public String loginPost(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				String plan = request.getParameter("type");
				sellPlanService.updateSellPlan(Byte.parseByte(plan));
				out.print(BaseString.INFO_UPDATE_SUCCESS);
			}else{
				out.print(BaseString.INFO_NO_OPERATE);
			}
		} catch (Exception e) {
			out.print(e.getMessage());
		}
		return null;
	}

	@RequestMapping(value = "/to_sellPlan_update", method = RequestMethod.GET)
	public String to_sellPlan_update(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (hasOperate(request)) {
				request.setAttribute("plan", sellPlanService.selectSellPlan());
				return "jsp/config/sell_plan";
			} else {
				request.setAttribute(BaseString.KEY_ERROR,BaseString.INFO_NO_OPERATE);
				return ToPage.HT_INFO;
			}
		} catch (Exception e) {
			request.setAttribute(BaseString.KEY_EXCEPTION, e);
			return ToPage.HT_INFO;
		}
	}
	/**
	 * 判断当前用户是否拥有权限
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return 是否拥有权限
	 */
	@SuppressWarnings("unchecked")
	public boolean hasOperate(HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Integer> operateArray = (List<Integer>) session.getAttribute("operateList");
		if (operateArray.contains(Operate.SELLPLAN_MANAGE)) {
			return true;
		} else {
			return false;
		}
	}
}
