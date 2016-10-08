package cn.hurry.controller.store;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.hurry.manage.store.StoreManage;
import cn.hurry.po.operate.Operate;
import cn.hurry.po.store.Store;
import cn.hurry.service.store.StoreService;
import cn.hurry.util.BaseString;
import cn.hurry.util.JSON;
import cn.hurry.util.ToPage;

@Controller
public class StoreController {

	@Resource
	StoreService storeService;

	/**
	 * 仓库列表
	 */
	@RequestMapping(value = "store_list_json")
	public String store_list_json(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			List<Store> data = StoreManage.getAllStore();
			//显示数据的json类型
			String jsonType = request.getParameter("type");
			//为null时默认为table
			int type = jsonType == null? JSON.TYPE_TABLE: Integer.parseInt(jsonType);
			String json = JSON.objects2JsonByType(data, data.size(), type);
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加仓库
	 */
	@RequestMapping(value = "store_add", method = RequestMethod.POST)
	public String store_add(HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				String json = request.getParameter("json");
				Store store = (Store) JSON.Json2Object(json, Store.class);
				storeService.insertStore(store);
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
	 *修改仓库页面
	 */
	@RequestMapping(value = "store_edit_page", method = RequestMethod.GET)
	public String store_edit_page(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (hasOperate(request)) {
				String id = request.getParameter("id");
				Store goods = StoreManage.getStoreById(Integer.parseInt(id));
				if (goods != null) {
					request.setAttribute("goods", goods);
					return "jsp/goods/store_edit";
				}
			} else {
				request.setAttribute(BaseString.KEY_ERROR,BaseString.INFO_NO_OPERATE);
				return ToPage.HT_INFO;
			}
		} catch (Exception e) {
			request.setAttribute(BaseString.KEY_EXCEPTION, e);
			return ToPage.HT_INFO;
		}
		request.setAttribute(BaseString.KEY_ERROR, "仓库不存在");
		return ToPage.HT_INFO;
	}

	/**
	 * 修改仓库
	 */
	@RequestMapping(value = "store_edit", method = RequestMethod.POST)
	public String store_edit(HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				String json = request.getParameter("json");
				Store store = (Store) JSON.Json2Object(json, Store.class);
				storeService.updateStore(store);
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
	 * 删除仓库
	 */
	@RequestMapping(value = "store_delete", method = RequestMethod.POST)
	public String store_delete(HttpServletRequest request,
			HttpServletResponse response, Store store) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				storeService.deleteStore(store);
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
		List<Integer> operateArray = (List<Integer>) session
				.getAttribute("operateList");
		if (operateArray.contains(Operate.UNIT_MANAGE)) {
			return true;
		} else {
			return false;
		}
	}
}
