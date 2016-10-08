package cn.hurry.controller.crm;

import java.io.PrintWriter;
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

import cn.hurry.json.JsonDecoder;
import cn.hurry.json.JsonEncoder;
import cn.hurry.po.crm.ClientInfo;
import cn.hurry.po.operate.Operate;
import cn.hurry.service.crm.ClientInfoService;
import cn.hurry.util.BaseString;
import cn.hurry.util.Pageutil;
import cn.hurry.util.StringUtil;
import cn.hurry.util.ToPage;

@Controller
public class ClientInfoController {

	@Resource
	ClientInfoService clientInfoService;

	@RequestMapping(value = "client_list_json")
	public String client_list_json(HttpServletRequest request, HttpServletResponse response, Pageutil pageUtil) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			String sortField = pageUtil.getSortField();
			String sortOrder = pageUtil.getSortOrder();
			 Map<String, Object> map = pageUtil.getQueryMap();
			// 分页数据
			int index = pageUtil.getPageIndex();
			int size = pageUtil.getPageSize();
			int dataSize = clientInfoService.countClientByMap(map);
			int firstResult = pageUtil.getStart();
			// 当前页没有数据后跳转到前一页
			if (pageUtil.getStart() >= dataSize && index != 0) {
				firstResult = (index - 1) * size;
			}
			map.put("maxResults", size);
			map.put("firstResult", firstResult);
			map.put("sortField", sortField != null ? sortField.replaceAll("#", ".") : null);
			map.put("sortOrder", sortOrder);
			List<ClientInfo> list = clientInfoService.selectClientByMap(map);
			// 将数据json化
			String json = "";
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("data", list);
			result.put("total", dataSize);
			json = JsonEncoder.getInstance(ClientInfo.class).encode(result);
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "client_info_add", method = RequestMethod.POST)
	public String client_info_add(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				String json = request.getParameter("json");
				ClientInfo clientInfo = JsonDecoder.getInstance(ClientInfo.class).decode(json);
				clientInfoService.insertClientInfo(clientInfo);
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

	@RequestMapping(value = "client_info_edit_page", method = RequestMethod.GET)
	public String client_info_edit_page(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (hasOperate(request)) {
				String id = request.getParameter("id");
				ClientInfo client_info = clientInfoService.selectClientById(Integer.parseInt(id));
				if (client_info != null) {
					request.setAttribute("info", client_info);
					return "jsp/crm/client/client_edit";
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

	@RequestMapping(value = "client_info_edit", method = RequestMethod.POST)
	public String client_info_edit(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				String json = request.getParameter("json");
				ClientInfo clientInfo = JsonDecoder.getInstance(ClientInfo.class).decode(json);
				clientInfoService.updateClientInfo(clientInfo);
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

	@RequestMapping(value = "client_info_delete", method = RequestMethod.POST)
	public String client_info_delete(HttpServletRequest request, HttpServletResponse response, ClientInfo ClientInfo) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			if (hasOperate(request)) {
				clientInfoService.deleteClientInfo(ClientInfo);
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
