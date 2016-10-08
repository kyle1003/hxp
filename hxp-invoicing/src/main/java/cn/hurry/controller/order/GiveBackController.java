package cn.hurry.controller.order;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.hurry.po.order.Order;
import cn.hurry.po.order.giveback.GiveBackInfo;
import cn.hurry.po.order.sell.SellOrderGoods;
import cn.hurry.po.user.User;
import cn.hurry.service.order.GiveBackService;
import cn.hurry.util.BaseString;
import cn.hurry.util.JSON;
import cn.hurry.util.Pageutil;
import cn.hurry.util.StringUtil;

@Controller
public class GiveBackController {

	@Resource
	private GiveBackService giveBackService;

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "giveBack_list_json")
	public String giveBack_list_json(HttpServletRequest request, HttpServletResponse response, Pageutil pageUtil) {
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
			int dataSize = giveBackService.countGiveBackInfoByMap(map);
			int firstResult = pageUtil.getStart();
			// 当前页没有数据后跳转到前一页
			if (pageUtil.getStart() >= dataSize && index != 0) {
				firstResult = (index - 1) * size;
			}
			map.put("maxResults", size);
			map.put("firstResult", firstResult);
			map.put("sortField", sortField != null ? sortField.replaceAll("#", ".") : null);
			map.put("sortOrder", sortOrder);
			List<GiveBackInfo> list = giveBackService.selectGiveBackInfoByMap(map);
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
	@RequestMapping(value = "sell_orderGoods_list_json")
	public String sell_orderGoods_list_json(HttpServletRequest request, HttpServletResponse response, Pageutil pageUtil) {
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
			int dataSize = giveBackService.countOrderGoods(map);
			int firstResult = pageUtil.getStart();
			// 当前页没有数据后跳转到前一页
			if (pageUtil.getStart() >= dataSize && index != 0) {
				firstResult = (index - 1) * size;
			}
			map.put("maxResults", size);
			map.put("firstResult", firstResult);
			map.put("sortField", sortField != null ? sortField.replaceAll("#", ".") : null);
			map.put("sortOrder", sortOrder);
			List<SellOrderGoods> list = giveBackService.selectOrderGoods(map);
			// 将数据json化
			String json = JSON.objects2JsonByType(list, dataSize, JSON.TYPE_TABLE);
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 添加退货
	 */
	@RequestMapping(value = "giveBack_add", method = RequestMethod.POST)
	public String giveBack_add(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				String json = request.getParameter("json");
				User user = (User)session.getAttribute("user");
				GiveBackInfo giveBackInfo = JSON.Json2Object(json, GiveBackInfo.class);
				giveBackInfo.setCreateUserId(user.getId());
				giveBackService.insertGiveBackInfo(giveBackInfo);
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
	
	public boolean hasOperate(HttpServletRequest request){
		return true;// hasOperate(request,100);
	}
	
	/**
	 * 判断当前用户是否拥有权限
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return 是否拥有权限
	 */
	@SuppressWarnings("unchecked")
	public boolean hasOperate(HttpServletRequest request, int operateId) {
		HttpSession session = request.getSession();
		List<Integer> operateArray = (List<Integer>) session.getAttribute("operateList");
		if (operateArray.contains(operateId)) {
			return true;
		} else {
			return false;
		}
	}
}
