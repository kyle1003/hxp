package cn.hurry.controller.goods;

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

import cn.hurry.manage.goods.GoodsManage;
import cn.hurry.po.goods.Goods;
import cn.hurry.po.operate.Operate;
import cn.hurry.service.goods.GoodsService;
import cn.hurry.util.BaseString;
import cn.hurry.util.JSON;
import cn.hurry.util.Pageutil;
import cn.hurry.util.StringUtil;
import cn.hurry.util.ToPage;

@Controller
public class GoodsController {

	@Resource
	GoodsService GoodsService;

	/**
	 * 商品列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "goods_list_json")
	public String goods_list_json(HttpServletRequest request, HttpServletResponse response,Pageutil pageutil) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			String key = request.getParameter("key");
			HashMap<String, String> map = key == null ? new HashMap<String, String>() : StringUtil.string2HashMap(key);
			map.put("sortField", pageutil.getSortField());
			map.put("sortOrder", pageutil.getSortOrder());
			
			List<Goods> data = GoodsManage.searchGoods(map);
			// 显示数据的json类型
			String jsonType = request.getParameter("type");
			// 为null时默认为table
			int type = jsonType == null ? JSON.TYPE_TABLE : Integer.parseInt(jsonType);
			String json = JSON.objects2JsonByType(data, data.size(), type);
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 商品列表2
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "goods_list_json_v2")
	public String goods_list_json_v2(HttpServletRequest request, HttpServletResponse response,Pageutil pageutil) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			String key = request.getParameter("key");
			HashMap<String, Object> map = key == null ? new HashMap<String, Object>() : StringUtil.string2HashMap(key);
			map.put("sortField", pageutil.getSortField());
			map.put("sortOrder", pageutil.getSortOrder());
			List<Goods> data =GoodsService.selectGoodsByMap(map);
			// 显示数据的json类型
			String jsonType = request.getParameter("type");
			// 为null时默认为table
			int type = jsonType == null ? JSON.TYPE_TABLE : Integer.parseInt(jsonType);
			String json = JSON.objects2JsonByType(data, data.size(), type);
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	  
	/**
	 * 添加商品
	 */
	@RequestMapping(value = "get_goods_by_code", method = RequestMethod.POST)
	public String get_goods_by_code(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		HashMap<String, String> jsonMap = new HashMap<String, String>();
		try {
			out = response.getWriter();
			String code = request.getParameter("code");
			List<Goods> goods = GoodsManage.getGoodsByPyCode(code);
			jsonMap.put("data", JSON.Encode(JSON.objectList2HashMapList(goods)));
			jsonMap.put("status", "1");
		} catch (Exception e) {
			jsonMap.put("status", e.getMessage());
			e.printStackTrace();
		}
		out.print(JSON.Encode(jsonMap));
		return null;
	}


	/**
	 * 添加商品
	 */
	@RequestMapping(value = "goods_add", method = RequestMethod.POST)
	public String goods_add(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				String json = request.getParameter("json");
				Goods Goods = (Goods) JSON.Json2Object(json, Goods.class);
				GoodsService.insertGoods(Goods);
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
	 *修改商品页面
	 */
	@RequestMapping(value = "goods_edit_page", method = RequestMethod.GET)
	public String goods_edit_page(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (hasOperate(request)) {
				String id = request.getParameter("id");
				Goods goods = GoodsManage.getGoodsById(Integer.parseInt(id));
				if (goods != null) {
					request.setAttribute("goods", goods);
					return "jsp/goods/goods_edit";
				}
			} else {
				request.setAttribute(BaseString.KEY_ERROR, BaseString.INFO_NO_OPERATE);
				return ToPage.HT_INFO;
			}
		} catch (Exception e) {
			request.setAttribute(BaseString.KEY_EXCEPTION, e);
			return ToPage.HT_INFO;
		}
		request.setAttribute(BaseString.KEY_ERROR, "商品不存在");
		return ToPage.HT_INFO;
	}

	/**
	 * 修改商品
	 */
	@RequestMapping(value = "goods_edit", method = RequestMethod.POST)
	public String goods_edit(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				String json = request.getParameter("json");
				Goods Goods = (Goods) JSON.Json2Object(json, Goods.class);
				GoodsService.updateGoods(Goods);
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
	 * 修改商品
	 */
	@RequestMapping(value = "goods_delete", method = RequestMethod.POST)
	public String goods_delete(HttpServletRequest request, HttpServletResponse response, Goods Goods) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				GoodsService.deleteGoods(Goods);
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
		if (operateArray.contains(Operate.GOODS_MANAGE)) {
			return true;
		} else {
			return false;
		}
	}
}
