package cn.hurry.controller.goods;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.hurry.manage.goods.GoodsTypeManage;
import cn.hurry.po.goods.GoodsType;
import cn.hurry.po.operate.Operate;
import cn.hurry.service.goods.GoodsTypeService;
import cn.hurry.util.BaseString;
import cn.hurry.util.JSON;

@Controller
public class GoodsTypeController {

	@Resource
	GoodsTypeService goodsTypeService;

	/**
	 * 商品类别列表
	 */
	@RequestMapping(value = "goodsType_list_json")
	public String goodsType_list_json(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			List<GoodsType> data = GoodsTypeManage.getAllGoodsType();
			String jsonType = request.getParameter("type");
			int type = jsonType == null ? JSON.TYPE_TABLE : Integer.parseInt(jsonType);
			String json = JSON.objects2JsonByType(data, data.size(), type);
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加商品类别
	 */
	@RequestMapping(value = "goodsType_add", method = RequestMethod.POST)
	public String goodsType_add(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				String json = request.getParameter("json");
				GoodsType goodsType = (GoodsType) JSON.Json2Object(json, GoodsType.class);
				goodsTypeService.insertGoodsType(goodsType);
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
	 * 修改商品类别
	 */
	@RequestMapping(value = "goodsType_edit", method = RequestMethod.POST)
	public String goodsType_edit(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				String json = request.getParameter("json");
				GoodsType goodsType = (GoodsType) JSON.Json2Object(json, GoodsType.class);
				goodsTypeService.updateGoodsType(goodsType);
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
	 * 删除商品类别
	 */
	@RequestMapping(value = "goodsType_delete", method = RequestMethod.POST)
	public String goodsType_delete(HttpServletRequest request, HttpServletResponse response, GoodsType goodsType) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				goodsTypeService.deleteGoodsType(goodsType);
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
