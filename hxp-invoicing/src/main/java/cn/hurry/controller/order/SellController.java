//package cn.hurry.controller.order;
//
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import cn.hurry.manage.succession.SuccessionManage;
//import cn.hurry.po.operate.Operate;
//import cn.hurry.po.order.Order;
//import cn.hurry.po.order.OrderGoods;
//import cn.hurry.po.succession.Succession;
//import cn.hurry.po.succession.SuccessionInfo;
//import cn.hurry.po.user.User;
//import cn.hurry.service.order.OrderService;
//import cn.hurry.service.print.PrintService;
//import cn.hurry.service.succession.SuccessionInfoService;
//import cn.hurry.service.succession.SuccessionService;
//import cn.hurry.util.BaseString;
//import cn.hurry.util.JSON;
//import cn.hurry.util.NumberUtil;
//import cn.hurry.util.Pageutil;
//import cn.hurry.util.StringUtil;
//import cn.hurry.util.ToPage;
//
//@Controller
//public class SellController {
//	@Resource
//	private OrderService orderService;
//
//	@Resource
//	private SuccessionService successionService;
//
//	@Resource
//	private SuccessionInfoService successionInfoService;
//
//	/**
//	 * 添加单据
//	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "sell_add", method = RequestMethod.POST)
//	public String sell_add(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//		PrintWriter out = null;
//		response.setContentType("text/html;charset=UTF-8");
//		try {
//			out = response.getWriter();
//			User user = (User) session.getAttribute("user");
//			if (hasOperate(request)) {
//				Object temp = session.getAttribute("order_goods_list_" + Order.TYPE_SELL_ORDER);
//				HashMap<Integer, OrderGoods> order_goods_list = temp == null ? new HashMap<Integer, OrderGoods>() : (HashMap<Integer, OrderGoods>) temp;
//				String json = request.getParameter("json");
//				Order order = JSON.Json2Object(json, Order.class);
//				double pay = 0.0;
//				ArrayList<OrderGoods> arrayList = new ArrayList<OrderGoods>(order_goods_list.values());
//				for (OrderGoods goods : arrayList) {
//					pay += goods.getPrice()*goods.getNumber();
//				}
//				order.setPay(NumberUtil.convert(pay));
//				order.setStoreId(-100);
//				order.setUserId(user.getId());
//				order.setCreateDate(new Date());
//				order.setType(Order.TYPE_SELL_ORDER);
//				order.setOrderGoods(arrayList);
//				order.setStatus(Order.STATUS_THROUGHCHECK);
//				String id = orderService.addCheckOutOrder(order);
//				session.removeAttribute("order_goods_list_" + Order.TYPE_SELL_ORDER);
//				session.removeAttribute("order_goods_id");
//				out.print(id);
//			} else {
//				out.print(BaseString.INFO_NO_OPERATE);
//			}
//		} catch (Exception e) {
//			out.print(e.getMessage());
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * 日结信息
//	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "successionInfo_list_json")
//	public String successionInfo_list_json(HttpServletRequest request, HttpServletResponse response, Pageutil pageUtil) {
//		try {
//			response.setContentType("text/html;charset=UTF-8");
//			String sortField = pageUtil.getSortField();
//			String sortOrder = pageUtil.getSortOrder();
//			HashMap<String, Object> map;
//			// 分页数据
//			int index = pageUtil.getPageIndex();
//			int size = pageUtil.getPageSize();
//			String key = pageUtil.getKey();
//			if (!StringUtil.isNullOrEmpty(key)) {
//				map = StringUtil.string2HashMap(key);
//			} else {
//				map = new HashMap<String, Object>();
//			}
//			int dataSize = successionInfoService.countSuccessionInfoByMap(map);
//			int firstResult = pageUtil.getStart();
//			// 当前页没有数据后跳转到前一页
//			if (pageUtil.getStart() >= dataSize && index != 0) {
//				firstResult = (index - 1) * size;
//			}
//			map.put("maxResults", size);
//			map.put("firstResult", firstResult);
//			map.put("sortField", sortField);
//			map.put("sortOrder", sortOrder);
//			List<SuccessionInfo> list = successionInfoService.selectSuccessionInfoByMap(map);
//			// 将数据json化
//			String json = JSON.objects2JsonByType(list, dataSize, JSON.TYPE_TABLE);
//			response.getWriter().write(json);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * 结班信息
//	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "succession_list_json")
//	public String succession_list_json(HttpServletRequest request, HttpServletResponse response, Pageutil pageUtil) {
//		try {
//			response.setContentType("text/html;charset=UTF-8");
//			String sortField = pageUtil.getSortField();
//			String sortOrder = pageUtil.getSortOrder();
//			HashMap<String, Object> map;
//			// 分页数据
//			int index = pageUtil.getPageIndex();
//			int size = pageUtil.getPageSize();
//			String key = pageUtil.getKey();
//			if (!StringUtil.isNullOrEmpty(key)) {
//				map = StringUtil.string2HashMap(key);
//			} else {
//				map = new HashMap<String, Object>();
//			}
//			int dataSize = successionService.countSuccessionByMap(map);
//			int firstResult = pageUtil.getStart();
//			// 当前页没有数据后跳转到前一页
//			if (pageUtil.getStart() >= dataSize && index != 0) {
//				firstResult = (index - 1) * size;
//			}
//			map.put("maxResults", size);
//			map.put("firstResult", firstResult);
//			map.put("sortField", sortField);
//			map.put("sortOrder", sortOrder);
//			List<Succession> list = successionService.selectSuccessionByMap(map);
//			// 将数据json化
//			String json = JSON.objects2JsonByType(list, dataSize, JSON.TYPE_TABLE);
//			response.getWriter().write(json);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * 开班
//	 */
//	@RequestMapping(value = "do_startwork", method = RequestMethod.POST)
//	public String do_startwork(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//		PrintWriter out = null;
//		HashMap<String, String> msg = new HashMap<String, String>();
//		response.setContentType("text/html;charset=UTF-8");
//		try {
//			out = response.getWriter();
//			User user = (User) session.getAttribute("user");
//			if (hasOperate(request)) {
//				Succession succession = new Succession();
//				succession.setTakeOverUserId(user.getId());
//				Succession old = successionService.doStartWork(succession);
//				msg.put("msg", "开班成功!开班人:" + user.getUsername());
//				msg.put("data", old.getId() + "");
//				msg.put("success", "true");
//			} else {
//				msg.put("success", "false");
//				msg.put("msg", BaseString.INFO_NO_OPERATE);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			msg.put("success", "false");
//			msg.put("msg", e.getMessage());
//		}
//		out.print(JSON.Encode(msg));
//		return null;
//	}
//
//	/**
//	 * 接班
//	 */
//	@RequestMapping(value = "do_takeover", method = RequestMethod.POST)
//	public String do_takeover(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//		PrintWriter out = null;
//		HashMap<String, String> msg = new HashMap<String, String>();
//		response.setContentType("text/html;charset=UTF-8");
//		try {
//			out = response.getWriter();
//			User user = (User) session.getAttribute("user");
//			if (hasOperate(request)) {
//				Succession succession = new Succession();
//				succession.setTakeOverUserId(user.getId());
//				Succession old = successionService.doTakOver(succession);
//				msg.put("msg", "开班成功!开班:" + user.getUsername() + "|上一班:" + old.getTakeOverUser().getUsername());
//				msg.put("data", old.getId() + "");
//				msg.put("success", "true");
//			} else {
//				msg.put("success", "false");
//				msg.put("msg", BaseString.INFO_NO_OPERATE);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			msg.put("success", "false");
//			msg.put("msg", e.getMessage());
//		}
//		out.print(JSON.Encode(msg));
//		return null;
//	}
//
//	/**
//	 * 结班
//	 */
//	@RequestMapping(value = "do_handsover", method = RequestMethod.POST)
//	public String do_handsover(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//		PrintWriter out = null;
//		HashMap<String, String> msg = new HashMap<String, String>();
//		response.setContentType("text/html;charset=UTF-8");
//		try {
//			out = response.getWriter();
//			User user = (User) session.getAttribute("user");
//			if (hasOperate(request)) {
//				Succession succession = SuccessionManage.getSuccessionByStatus(Succession.STATUS_WORKING);
//				if (succession == null || succession.getTakeOverUserId() != user.getId()) {
//					msg.put("success", "false");
//					msg.put("data", "获取交接班信息错误");
//				} else {
//					successionService.doHandOver(succession);
//					msg.put("msg", "结班成功!");
//					msg.put("data", succession.getId() + "");
//					msg.put("success", "true");
//				}
//			} else {
//				msg.put("success", "false");
//				msg.put("msg", BaseString.INFO_NO_OPERATE);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			msg.put("success", "false");
//			msg.put("msg", e.getMessage());
//		}
//		out.print(JSON.Encode(msg));
//		return null;
//	}
//
//	/**
//	 * 下班 日结
//	 */
//	@RequestMapping(value = "do_workover", method = RequestMethod.POST)
//	public String do_workover(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//		PrintWriter out = null;
//		HashMap<String, String> msg = new HashMap<String, String>();
//		response.setContentType("text/html;charset=UTF-8");
//		try {
//			out = response.getWriter();
//			User user = (User) session.getAttribute("user");
//			if (hasOperate(request)) {
//				Succession succession = SuccessionManage.getSuccessionByStatus(Succession.STATUS_WORKING);
//				if (succession == null || succession.getTakeOverUserId() != user.getId()) {
//					msg.put("success", "false");
//					msg.put("msg", "获取交接班信息错误");
//				} else {
//					successionService.doOver();
//					msg.put("msg", "日结成功!");
//					msg.put("data", succession.getId() + "");
//					msg.put("success", "true");
//				}
//			} else {
//				msg.put("success", "false");
//				msg.put("msg", BaseString.INFO_NO_OPERATE);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			msg.put("success", "false");
//			msg.put("msg", e.getMessage());
//		}
//		out.print(JSON.Encode(msg));
//		return null;
//	}
//
//	@Resource
//	private PrintService printService;
//
//	/**
//	 * 获取接班打印数据
//	 */
//	@RequestMapping(value = "get_workover_printData")
//	public String get_workover_printData(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//		PrintWriter out = null;
//		HashMap<String, String> msg = new HashMap<String, String>();
//		response.setContentType("text/html;charset=UTF-8");
//		try {
//			out = response.getWriter();
//			if (hasOperate(request)) {
//				int id = Integer.parseInt(request.getParameter("id"));
//				boolean all = Boolean.parseBoolean(request.getParameter("all"));
//				msg.put("msg", "1");
//				msg.put("data", printService.encodeBase64Data(printService.createWorkOverSellInfoPrintData(id, all)));
//				msg.put("success", "true");
//			} else {
//				msg.put("success", "false");
//				msg.put("msg", BaseString.INFO_NO_OPERATE);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			msg.put("success", "false");
//			msg.put("msg", e.getMessage());
//		}
//		out.print(JSON.Encode(msg));
//		return null;
//	}
//
//	/**
//	 * 获取接班打印数据
//	 */
//	@RequestMapping(value = "get_sellInfo_printData")
//	public String get_sellInfo_printData(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//		PrintWriter out = null;
//		HashMap<String, String> msg = new HashMap<String, String>();
//		response.setContentType("text/html;charset=UTF-8");
//		try {
//			out = response.getWriter();
//			if (hasOperate(request)) {
//				String id = request.getParameter("id");
//				String zl = request.getParameter("zl");
//				String sdp = request.getParameter("sdp");
//				String sf = request.getParameter("sf");
//				msg.put("msg", "1");
//				msg.put("data", printService.encodeBase64Data(printService.createSellPrintData(id, zl, sdp, sf)));
//				msg.put("success", "true");
//			} else {
//				msg.put("success", "false");
//				msg.put("msg", BaseString.INFO_NO_OPERATE);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			msg.put("success", "false");
//			msg.put("msg", e.getMessage());
//		}
//		out.print(JSON.Encode(msg));
//		return null;
//	}
//
//	/**
//	 * 单据明细
//	 */
//	@RequestMapping(value = "sell_order_info", method = RequestMethod.GET)
//	public String sell_order_info(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			if (hasOperate(request)) {
//				String id = request.getParameter("id");
//				String zl = request.getParameter("zl");
//				String sdp = request.getParameter("sdp");
//				String sf = request.getParameter("sf");
//				Order order = orderService.selectOrderById(id);
//				request.setAttribute("order", order);
//				request.setAttribute("zl", zl);
//				request.setAttribute("sdp", sdp);
//				request.setAttribute("sf", sf);
//				return "jsp/print/sell_info_print";
//			} else {
//				request.setAttribute(BaseString.KEY_ERROR, BaseString.INFO_NO_OPERATE);
//				return ToPage.HT_INFO;
//			}
//		} catch (Exception e) {
//			request.setAttribute(BaseString.KEY_EXCEPTION, e);
//			e.printStackTrace();
//			return ToPage.HT_INFO;
//		}
//	}
//
//	/**
//	 * 日结单据明细
//	 */
//	@RequestMapping(value = "sell_order_info_workOver", method = RequestMethod.GET)
//	public String sell_order_info_workOver(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			if (hasOperate(request)) {
//				String id = request.getParameter("id");
//				String scId = request.getParameter("scId");
//				List<Succession> successions = null;
//				HashMap<String, Object> map = new HashMap<String, Object>();
//				if (StringUtil.isNumber(scId)) {
//					map.put("successionInfoId", scId);
//					request.setAttribute("printAll",true);
//					successions = successionService.selectSuccessionByMap(map);
//					request.setAttribute("id", successions.size()>0?successions.get(0).getId():0);
//				} else {
//					Succession succession = successionService.selectSuccessionById(Integer.parseInt(id));
//					map.put("successionInfoId", succession.getSuccessionInfoId());
//					request.setAttribute("id", id);
//					request.setAttribute("printAll",false);
//					successions = successionService.selectSuccessionByMap(map);
//				}
//				request.setAttribute("successions", successions);
//				request.setAttribute("showAll", request.getParameter("showAll"));
//				request.setAttribute("info", request.getParameter("info"));
//				request.setAttribute("type", request.getParameter("type"));
//				request.setAttribute("all", request.getParameter("all"));
//
//				return "jsp/print/sell_info_print_workOver";
//			} else {
//				request.setAttribute(BaseString.KEY_ERROR, BaseString.INFO_NO_OPERATE);
//				return ToPage.HT_INFO;
//			}
//		} catch (Exception e) {
//			request.setAttribute(BaseString.KEY_EXCEPTION, e);
//			e.printStackTrace();
//			return ToPage.HT_INFO;
//		}
//	}
//
//	/**
//	 * 交班单据明细
//	 */
//	@RequestMapping(value = "sell_order_info_succession", method = RequestMethod.GET)
//	public String sell_order_info_succession(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			if (hasOperate(request)) {
//				String id = request.getParameter("id");
//				Succession succession = successionService.selectSuccessionById(Integer.parseInt(id));
//				List<Succession> successions = new ArrayList<Succession>();
//				successions.add(succession);
//				request.setAttribute("successions", successions);
//				request.setAttribute("showAll", request.getParameter("showAll"));
//				request.setAttribute("info", request.getParameter("info"));
//				request.setAttribute("type", request.getParameter("type"));
//				request.setAttribute("all", request.getParameter("all"));
//				request.setAttribute("printAll",false);
//				request.setAttribute("id", id);
//				// request.setAttribute("orderGoods_list", new ArrayList<OrderGoods>(map.values()));
//				return "jsp/print/sell_info_print_workOver";
//			} else {
//				request.setAttribute(BaseString.KEY_ERROR, BaseString.INFO_NO_OPERATE);
//				return ToPage.HT_INFO;
//			}
//		} catch (Exception e) {
//			request.setAttribute(BaseString.KEY_EXCEPTION, e);
//			e.printStackTrace();
//			return ToPage.HT_INFO;
//		}
//	}
//
//	/**
//	 * 交班单据明细
//	 */
//	@RequestMapping(value = "my_sell_order_info_succession", method = RequestMethod.GET)
//	public String my_sell_order_info_succession(HttpServletRequest request, HttpServletResponse response) {
//		try {
//				Succession succession = SuccessionManage.getSuccessionByStatus(Succession.STATUS_WORKING);
//				if(succession==null){
//					succession=SuccessionManage.getSuccessionByStatus(Succession.STATUS_WORKOVER_BUT_NOT_HANDOVER);
//				}if(succession==null){
//					succession=SuccessionManage.getSuccessionByStatus(Succession.STATUS_WORKOVER);
//				}
//				List<Succession> successions = new ArrayList<Succession>();
//				successions.add(succession);
//				request.setAttribute("successions", successions);
//				request.setAttribute("showAll", request.getParameter("showAll"));
//				request.setAttribute("info", request.getParameter("info"));
//				request.setAttribute("type", request.getParameter("type"));
//				request.setAttribute("all", request.getParameter("all"));
//				request.setAttribute("printAll",false);
//				request.setAttribute("id", succession.getId());
//				// request.setAttribute("orderGoods_list", new ArrayList<OrderGoods>(map.values()));
//				return "jsp/print/sell_info_print_workOver";
//		} catch (Exception e) {
//			request.setAttribute(BaseString.KEY_EXCEPTION, e);
//			e.printStackTrace();
//			return ToPage.HT_INFO;
//		}
//	}
//
//
//	/**
//	 * 判断当前用户是否拥有权限
//	 *
//	 * @param request
//	 *            HttpServletRequest对象
//	 * @return 是否拥有权限
//	 */
//	@SuppressWarnings("unchecked")
//	public boolean hasOperate(HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		List<Integer> operateArray = (List<Integer>) session.getAttribute("operateList");
//		if (operateArray.contains(Operate.CHECKOUT_MANAGE)) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//}
