package cn.hurry.controller.order;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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

import cn.hurry.po.operate.Operate;
import cn.hurry.po.order.Order;
import cn.hurry.po.order.OrderGoods;
import cn.hurry.po.order.buy.BuyOrderGoods;
import cn.hurry.po.order.sell.SellInfo;
import cn.hurry.service.order.OrderService;
import cn.hurry.util.BaseString;
import cn.hurry.util.JSON;
import cn.hurry.util.Pageutil;
import cn.hurry.util.StringUtil;
import cn.hurry.util.ToPage;

@Controller
public class OrderController {

	@Resource
	OrderService orderService;

	/**
	 * 单据列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "order_list_json")
	public String order_list_json(HttpServletRequest request, HttpServletResponse response, Pageutil pageUtil) {
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
			int dataSize = orderService.countOrderByMap(map);
			int firstResult = pageUtil.getStart();
			// 当前页没有数据后跳转到前一页
			if (pageUtil.getStart() >= dataSize && index != 0) {
				firstResult = (index - 1) * size;
			}
			map.put("maxResults", size);
			map.put("firstResult", firstResult);
			map.put("sortField", sortField != null ? sortField.replaceAll("#", ".") : null);
			map.put("sortOrder", sortOrder);
			List<Order> list = orderService.selectOrderByMap(map);
			// 将数据json化
			String json = JSON.objects2JsonByType(list, dataSize, JSON.TYPE_TABLE);
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 单据商品集合
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "order_goods_list_session", method = RequestMethod.POST)
	public String order_goods_list_session(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				String type = request.getParameter("type");
				Object temp = session.getAttribute("order_goods_list_" + type);
				HashMap<Integer, OrderGoods> order_goods_list = temp == null ? new HashMap<Integer, OrderGoods>() : (HashMap<Integer, OrderGoods>) temp;
				List<OrderGoods> list = new ArrayList<OrderGoods>(order_goods_list.values());
				// 将数据json化
				String json = JSON.objects2JsonByType(list, list.size(), JSON.TYPE_TABLE);
				out.print(json);
			} else {
			}
		} catch (Exception e) {
			out.print(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除单据商品
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "order_goods_delete_session", method = RequestMethod.POST)
	public String order_goods_delete_session(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				String idstr = request.getParameter("id");
				String type = request.getParameter("type");
				Object temp = session.getAttribute("order_goods_list_" + type);
				HashMap<Integer, OrderGoods> order_goods_list = temp == null ? new HashMap<Integer, OrderGoods>() : (HashMap<Integer, OrderGoods>) temp;
				// 将数据json化
				order_goods_list.remove(Integer.parseInt(idstr));
				out.print(BaseString.INFO_DEL_SUCCESS);
			} else {
				out.print(BaseString.INFO_NO_OPERATE);
			}
		} catch (Exception e) {
			out.print(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加商品到单据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "order_goods_add_session", method = RequestMethod.POST)
	public String order_create_session(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				String type = request.getParameter("type");
				Object temp = session.getAttribute("order_goods_list_" + type);
				Object id_temp = session.getAttribute("order_goods_id");
				int id = id_temp == null ? 0 : (Integer) id_temp;
				id--;
				HashMap<Integer, OrderGoods> order_goods_list = temp == null ? new HashMap<Integer, OrderGoods>() : (HashMap<Integer, OrderGoods>) temp;
				String json = request.getParameter("json");
				OrderGoods orderGoods = (OrderGoods) JSON.Json2Object(json, OrderGoods.class);
				if ((Order.TYPE_SELL_ORDER + "").equals(type)) {
					if (orderGoods.getGoods().getNumber() < orderGoods.getNumber()) {
						out.print("库存不足!");
						return null;
					}
				}

				orderGoods.setId(id);
				// 相同的商品，并且价格也相同则叠加数量
				List<OrderGoods> list = new ArrayList<OrderGoods>(order_goods_list.values());
				for (OrderGoods orderGoods2 : list) {
					if (orderGoods2.getGoodsId() == orderGoods.getGoodsId() && orderGoods2.getPrice() == orderGoods.getPrice()) {
						orderGoods.setId(orderGoods2.getId());
						orderGoods.setNumber(orderGoods.getNumber() + orderGoods2.getNumber());
					}
				}
				order_goods_list.put(orderGoods.getId(), orderGoods);
				session.setAttribute("order_goods_list_" + type, order_goods_list);
				session.setAttribute("order_goods_id", id);
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

	/**
	 * 编辑商品到单据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "order_goods_edit_session", method = RequestMethod.POST)
	public String order_goods_edit_session(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				String type = request.getParameter("type");
				Object temp = session.getAttribute("order_goods_list_" + type);
				HashMap<Integer, OrderGoods> order_goods_list = temp == null ? new HashMap<Integer, OrderGoods>() : (HashMap<Integer, OrderGoods>) temp;
				String json = request.getParameter("json");
				OrderGoods orderGoods = (OrderGoods) JSON.Json2Object(json, OrderGoods.class);
				if (orderGoods.getGoods().getNumber() < orderGoods.getNumber()) {
					out.print("库存不足!");
					return null;
				} else {
					order_goods_list.put(orderGoods.getId(), orderGoods);
					session.setAttribute("order_goods_list_" + type, order_goods_list);
					out.print(BaseString.INFO_UPDATE_SUCCESS);
				}
			} else {
				out.print(BaseString.INFO_NO_OPERATE);
			}
		} catch (Exception e) {
			out.print(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加单据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "order_add", method = RequestMethod.POST)
	public String order_add(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			String type = request.getParameter("type");
			if (hasOperate(request, getOperateByType(Integer.parseInt(type), "add"))) {
				Object temp = session.getAttribute("order_goods_list_" + type);
				HashMap<Integer, OrderGoods> order_goods_list = temp == null ? new HashMap<Integer, OrderGoods>() : (HashMap<Integer, OrderGoods>) temp;
				String json = request.getParameter("json");
				Order order = (Order) JSON.Json2Object(json, Order.class);
				order.setOrderGoods(new ArrayList<OrderGoods>(order_goods_list.values()));
				out.print("添加订单:" + orderService.insertOrder(order) + "成功,待审核!");
				session.removeAttribute("order_goods_list_" + type);
				session.removeAttribute("order_goods_id");
			} else {
				out.print(BaseString.INFO_NO_OPERATE);
			}
		} catch (Exception e) {
			out.print(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除单据
	 */
	@RequestMapping(value = "order_delete", method = RequestMethod.POST)
	public String order_delete(HttpServletRequest request, HttpServletResponse response, Order order) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				order.setStatus(Order.STATUS_DELETE);
				orderService.updateOrder(order);
				out.print(BaseString.INFO_UPDATE_SUCCESS);
			} else {
				out.print(BaseString.INFO_NO_OPERATE);
			}
		} catch (Exception e) {
			out.print(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 审核单据
	 */
	@RequestMapping(value = "order_check", method = RequestMethod.POST)
	public String order_check(HttpServletRequest request, HttpServletResponse response, Order order) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request, getOperateByType(order.getType(), "check"))) {
				orderService.updateOrder(order);
				out.print(BaseString.INFO_UPDATE_SUCCESS);
			} else {
				out.print(BaseString.INFO_NO_OPERATE);
			}
		} catch (Exception e) {
			out.print(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 审核单据
	 */
	@RequestMapping(value = "get_shelfLife_timeover", method = RequestMethod.POST)
	public String get_shelfLife_timeover(HttpServletRequest request, HttpServletResponse response, Order order) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("surplusNumberNot", 0);
				map.put("shelfLifeDate", new Date());
				List<BuyOrderGoods> buyOrderGoodes = orderService.selectOrderGoodsByMap(map);
				Map<String, Object> data = new HashMap<String, Object>();
				StringBuilder builder = new StringBuilder();
				for (BuyOrderGoods buyorderGoods : buyOrderGoodes) {
					builder.append(buyorderGoods.getGoods().getName());
					builder.append(",");
				}
				data.put("dataSize", buyOrderGoodes.size());
				data.put("names", builder.toString());
				out.print(JSON.Encode(data));
			} else {
				out.print(BaseString.INFO_NO_OPERATE);
			}
		} catch (Exception e) {
			out.print(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 单据明细
	 */
	@RequestMapping(value = "order_info", method = RequestMethod.GET)
	public String order_info(HttpServletRequest request, HttpServletResponse response) {
		try {
			String type = request.getParameter("type");
			String edit = request.getParameter("edit");
			if (hasOperate(request, getOperateByType(Integer.parseInt(type), "show"))) {
				String id = request.getParameter("id");
				Order order = orderService.selectOrderById(id);
				request.setAttribute("order", order);
				if (edit != null && edit.equals("edit") && order.getStatus() == Order.STATUS_NOTCHECKED) {
					return "jsp/order/order_edit";
				}
				return "jsp/order/order_info";
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
	 * 销售明细
	 */
	@RequestMapping(value = "sell_info", method = RequestMethod.GET)
	public String sell_info(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (hasOperate(request)) {
				String id = request.getParameter("id");
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("sellOrderId", id);
				Order order = orderService.selectOrderById(id);
				List<SellInfo> infos = orderService.selectSellInfoByMap(map);
				request.setAttribute("order", order);
				request.setAttribute("infos", infos);
				return "jsp/count/sell_info";
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
	 * 修改商品信息单据
	 */
	@RequestMapping(value = "order_edit", method = RequestMethod.POST)
	public String order_edit(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				String json = request.getParameter("json");
				orderService.updateOrderInfo(json2OrderInfo(json));
				out.print(BaseString.INFO_UPDATE_SUCCESS);
			} else {
				out.print(BaseString.INFO_NO_OPERATE);
			}
		} catch (Exception e) {
			out.print(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Order json2OrderInfo(String json) throws Exception {
		Order order = null;
		List<OrderGoods> list = new ArrayList<OrderGoods>();
		for (HashMap map : JSON.json2list(json)) {
			String id = map.get("id").toString();
			order = orderService.selectOrderById(id);
			order.setStoreId(Integer.parseInt(map.get("storeId").toString()));
			order.setUserId(Integer.parseInt(map.get("userId").toString()));
			order.setPay(Double.parseDouble(map.get("pay").toString()));
			List<OrderGoods> old = order.getOrderGoods();
			for (OrderGoods goods : old) {
				goods.setNumber(Double.parseDouble(map.get("number" + (goods.getId())).toString()));
				goods.setPrice(Double.parseDouble(map.get("price" + (goods.getId())).toString()));
				list.add(goods);
			}
		}
		return order;
	}

	/**
	 * 判断当前用户是否拥有权限
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return 是否拥有权限
	 */
	public boolean hasOperate(HttpServletRequest request) {
		return true;
	}

	public int getOperateByType(int type, String operate) {
		if (operate.equals("check")) {
			switch (type) {
			case Order.TYPE_BUY_ORDER:
				return Operate.BUY_ORDER_CHECK_MANAGE;
			case Order.TYPE_SELL_ORDER:
				return Operate.SELL_ORDER_CHECK_MANAGE;
			default:
				return 404;
			}
		} else {
			switch (type) {
			case Order.TYPE_BUY_ORDER:
				return Operate.BUY_MANAGE;
			case Order.TYPE_SELL_ORDER:
				return Operate.SELL_MANAGE;
			default:
				return 404;
			}
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
