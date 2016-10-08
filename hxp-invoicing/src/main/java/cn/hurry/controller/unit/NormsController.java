package cn.hurry.controller.unit;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.hurry.manage.unit.NormsManage;
import cn.hurry.po.operate.Operate;
import cn.hurry.po.unit.Norms;
import cn.hurry.service.unit.NormsService;
import cn.hurry.util.BaseString;
import cn.hurry.util.JSON;
import cn.hurry.util.ToPage;

@Controller
public class NormsController {

	@Resource
	NormsService normsService;

	/**
	 * 规格列表
	 */
	@RequestMapping(value = "norms_list_json")
	public String norms_list_json(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			List<Norms> data = NormsManage.getAllNorms();
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
	 * 添加规格
	 */
	@RequestMapping(value = "norms_add", method = RequestMethod.POST)
	public String norms_add(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				String json = request.getParameter("json");
				String get =  request.getParameter("get");
				Norms Norms = (Norms) JSON.Json2Object(json, Norms.class);
				int id = normsService.insertNorms(Norms);
				//页面需要的数据 如返回添加成功后的id
				if(get==null){
					out.print(BaseString.INFO_ADD_SUCCESS);
				}else{
					out.print(id);
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
	 *修改规格页面
	 */
	@RequestMapping(value = "norms_edit_page", method = RequestMethod.GET)
	public String norms_edit_page(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (hasOperate(request)) {
				String id = request.getParameter("id");
				Norms goods = NormsManage.getNormsById(Integer.parseInt(id));
				if (goods != null) {
					request.setAttribute("goods", goods);
					return "jsp/goods/norms_edit";
				}
			} else {
				request.setAttribute(BaseString.KEY_ERROR, BaseString.INFO_NO_OPERATE);
				return ToPage.HT_INFO;
			}
		} catch (Exception e) {
			request.setAttribute(BaseString.KEY_EXCEPTION, e);
			return ToPage.HT_INFO;
		}
		request.setAttribute(BaseString.KEY_ERROR, "规格不存在");
		return ToPage.HT_INFO;
	}

	/**
	 * 修改规格
	 */
	@RequestMapping(value = "norms_edit", method = RequestMethod.POST)
	public String norms_edit(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				String json = request.getParameter("json");
				Norms Norms = (Norms) JSON.Json2Object(json, Norms.class);
				normsService.updateNorms(Norms);
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
	 * 删除规格
	 */
	@RequestMapping(value = "norms_delete", method = RequestMethod.POST)
	public String norms_delete(HttpServletRequest request, HttpServletResponse response, Norms Norms) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				normsService.deleteNorms(Norms);
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
		if (operateArray.contains(Operate.UNIT_MANAGE)) {
			return true;
		} else {
			return false;
		}
	}
}
