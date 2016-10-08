package cn.hurry.controller.inventory;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.hurry.po.inventory.Inventory;
import cn.hurry.po.inventory.InventoryGoods;
import cn.hurry.po.operate.Operate;
import cn.hurry.service.inventory.InventoryService;
import cn.hurry.util.BaseString;
import cn.hurry.util.JSON;
import cn.hurry.util.Pageutil;
import cn.hurry.util.StringUtil;
import cn.hurry.util.ToPage;

@Controller
public class InventoryController {

	@Resource
	InventoryService inventoryService;

	/**
	 * 盘点列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "inventory_list_json")
	public String goods_list_json(HttpServletRequest request, HttpServletResponse response, Pageutil pageUtil) {
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
			int dataSize = inventoryService.countInventoryByMap(map);
			int firstResult = pageUtil.getStart();
			// 当前页没有数据后跳转到前一页
			if (pageUtil.getStart() >= dataSize && index != 0) {
				firstResult = (index - 1) * size;
			}
			map.put("maxResults", size);
			map.put("firstResult", firstResult);
			map.put("sortField", sortField != null ? sortField.replaceAll("#", ".") : null);
			map.put("sortOrder", sortOrder);
			List<Inventory> list = inventoryService.selectInventoryByMap(map);
			// 将数据json化
			String json = JSON.objects2JsonByType(list, dataSize, JSON.TYPE_TABLE);
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加盘点
	 */
	@RequestMapping(value = "inventory_add", method = RequestMethod.POST)
	public String inventory_add(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				String json = request.getParameter("json");
				Inventory inventory = (Inventory) JSON.Json2Object(json, Inventory.class);
				inventoryService.insertInventory(inventory);
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
	 *修改盘点商品页面
	 */
	@RequestMapping(value = "inventory_edit_page", method = RequestMethod.GET)
	public String inventory_edit_page(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (hasOperate(request)) {
				String id = request.getParameter("id");
				Inventory inventory = inventoryService.selectInventoryById(Integer.parseInt(id));
				if (inventory != null) {
					request.setAttribute("inventory", inventory);
					if (inventory.getStatus() == Inventory.STATUS_NOT_CHECKED) {
						return "jsp/inventory/inventory_edit";
					} else {
						return "jsp/inventory/inventory_info";
					}
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
	 *盘点信息
	 */
	@RequestMapping(value = "inventory_info", method = RequestMethod.GET)
	public String inventory_info(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (hasOperate(request)) {
				String id = request.getParameter("id");
				Inventory inventory = inventoryService.selectInventoryById(Integer.parseInt(id));
				if (inventory != null) {
					request.setAttribute("inventory", inventory);
					return "jsp/inventory/inventory_info";
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
	 * 修改盘点
	 */
	@RequestMapping(value = "inventory_update", method = RequestMethod.POST)
	public String inventory_update(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				String json = request.getParameter("json");
				inventoryService.updateInventory(json2Inventory(json));
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
	
	public Inventory json2Inventory(String json) throws Exception{
		Inventory inventory = (Inventory)JSON.Json2Object(json, Inventory.class);
		List<InventoryGoods> goods =inventoryService.selectInventoryGoodsByInventoryId(inventory.getId());
		List<InventoryGoods> newgoods = new ArrayList<InventoryGoods>();
		List<HashMap<?, ?>> list = JSON.json2list(json);
		for(HashMap<?, ?> map:list){
			for(InventoryGoods inventoryGoods:goods){
				inventoryGoods.setRealNumber(Double.parseDouble(map.get("realNumber"+inventoryGoods.getId()).toString()));
				newgoods.add(inventoryGoods);
			}
		}
		inventory.setInventoryGoods(newgoods);
		return inventory;
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
		if (operateArray.contains(Operate.INVENTORY_GOODS_MANAGE)) {
			return true;
		} else {
			return false;
		}
	}
}
