package cn.hurry.controller.loss;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.hurry.po.loss.Loss;
import cn.hurry.po.operate.Operate;
import cn.hurry.po.order.Order;
import cn.hurry.po.order.buy.BuyOrderGoods;
import cn.hurry.po.user.User;
import cn.hurry.service.loss.LossService;
import cn.hurry.util.BaseString;
import cn.hurry.util.JSON;
import cn.hurry.util.Pageutil;
import cn.hurry.util.StringUtil;
import cn.hurry.util.ToPage;

@Controller
public class LossController {

	@Resource
	LossService LossService;

	/**
	 * 商品列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "loss_list_json")
	public String loss_list_json(HttpServletRequest request, HttpServletResponse response, Pageutil pageUtil) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			String sortField = pageUtil.getSortField();
			String sortOrder = pageUtil.getSortOrder();
			HashMap<String, Object> map;
			// 分页数据
			int index = pageUtil.getPageIndex();
			int size = pageUtil.getPageSize();
			String key = pageUtil.getKey();
			if (!StringUtil.isNullOrEmpty(key)) {
				map = StringUtil.string2HashMap(key);
			} else {
				map = new HashMap<String, Object>();
			}
			int dataSize = LossService.countLossByMap(map);
			int firstResult = pageUtil.getStart();
			// 当前页没有数据后跳转到前一页
			if (pageUtil.getStart() >= dataSize && index != 0) {
				firstResult = (index - 1) * size;
			}
			map.put("maxResults", size);
			map.put("firstResult", firstResult);
			map.put("sortField", sortField != null ? sortField.replaceAll("#", ".") : null);
			map.put("sortOrder", sortOrder);
			List<Loss> list = LossService.selectLossByMap(map);
			// 将数据json化
			String json = JSON.objects2JsonByType(list, dataSize, JSON.TYPE_TABLE);
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 采购单商品列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "orderGoods_list_json")
	public String orderGoods_list_json(HttpServletRequest request, HttpServletResponse response, Pageutil pageUtil) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			String sortField = pageUtil.getSortField();
			String sortOrder = pageUtil.getSortOrder();
			HashMap<String, Object> map;
			// 分页数据
			int index = pageUtil.getPageIndex();
			int size = pageUtil.getPageSize();
			String key = pageUtil.getKey();
			if (!StringUtil.isNullOrEmpty(key)) {
				map = StringUtil.string2HashMap(key);
			} else {
				map = new HashMap<String, Object>();
			}
			map.put("status", Order.STATUS_THROUGHCHECK);// 已生效的单据
			int dataSize = LossService.countOrderGoods(map);
			int firstResult = pageUtil.getStart();
			// 当前页没有数据后跳转到前一页
			if (pageUtil.getStart() >= dataSize && index != 0) {
				firstResult = (index - 1) * size;
			}
			map.put("maxResults", size);
			map.put("firstResult", firstResult);
			map.put("sortField", sortField != null ? sortField.replaceAll("#", ".") : null);
			map.put("sortOrder", sortOrder);
			List<BuyOrderGoods> list = LossService.selectOrderGoods(map);
			// 将数据json化
			String json = JSON.objects2JsonByType(list, dataSize, JSON.TYPE_TABLE);
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加损耗
	 */
	@RequestMapping(value = "loss_add", method = RequestMethod.POST)
	public String loss_add(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				String json = request.getParameter("json");
				List<Loss> Loss = json2LossList(json);
				User user = (User) session.getAttribute("user");
				for (Loss loss : Loss) {
					loss.setUserId(user.getId());
				}
				LossService.insertLosses(Loss);
				out.print(BaseString.INFO_ADD_SUCCESS);
			} else {
				out.print(BaseString.INFO_NO_OPERATE);
			}
		} catch (Exception e) {
			out.print(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public List<Loss> json2LossList(String json) throws Exception {
		List<Loss> list = new ArrayList<Loss>();
		try {
			List<HashMap<?, ?>> maplist = JSON.json2list(json);
			for (HashMap<?, ?> map : maplist) {
				Loss loss = new Loss();
				loss.setOrderGoodsId(Integer.parseInt(map.get("oid").toString()));
				loss.setRemark(map.get("remark").toString());
				loss.setNumber(Double.parseDouble(map.get("lossnumber").toString()));
				list.add(loss);
			}
		} catch (Exception e) {
			throw new Exception("数据格式错误,请检查后重试!");
		}
		return list;
	}

	/**
	 * 销售明细
	 */
	@RequestMapping(value = "loss_info", method = RequestMethod.GET)
	public String loss_info(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (hasOperate(request)) {
				String ids = request.getParameter("ids");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("ids", ids);
				request.setAttribute("loss", LossService.selectLossByMap(map));
				return "jsp/loss/loss_info";
			} else {
				request.setAttribute(BaseString.KEY_ERROR, BaseString.INFO_NO_OPERATE);
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
		if (operateArray.contains(Operate.LOSS_GOODS_MANAGE)) {
			return true;
		} else {
			return false;
		}
	}
}
