package cn.hurry.controller.operate;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.hurry.manage.OperateManage;
import cn.hurry.po.operate.ManagerGroup;
import cn.hurry.po.operate.Operate;
import cn.hurry.po.operate.OperateGroup;
import cn.hurry.service.operate.ManagerGroupService;
import cn.hurry.service.operate.OperateService;
import cn.hurry.util.BaseString;
import cn.hurry.util.DateTimeUtils;
import cn.hurry.util.JSON;
import cn.hurry.util.MapUtil;
import cn.hurry.util.Pageutil;
import cn.hurry.util.ToPage;

/**
 * 管理组类控制器
 * 
 * @author zh.sqy@qq.com
 * 
 */
@Controller
public class ManageGroupController {

	/**
	 * 串行标示
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 管理组业务类
	 */
	@Resource
	private ManagerGroupService managerGroupService;
	
	@Resource
	private OperateService operateService;

	/**
	 * 进入添加管理组页面
	 */
	@RequestMapping(value = "/managerGroup_doAdd", method = RequestMethod.GET)
	public String managerGroup_doAdd() {
		return "jsp/operate/add/addmanagerGroup";
	}

	/**
	 * 获取管理组（在页面中的下拉列表）
	 */
	@RequestMapping(value = "/getManagerGroup", method = RequestMethod.GET)
	public String getManagerGroup(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			out.print(encodeManagerGroupData(managerGroupService.selectAllManagerGroup()));
		} catch (Exception e) {
			out.print(e.getMessage());
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 处理管理组数据（在页面中的下拉列表）
	 */
	public String encodeManagerGroupData(List<ManagerGroup> list) {
		StringBuffer sb = new StringBuffer();
		int index = 0;
		sb.append("[");
		for (ManagerGroup group : list) {
			index++;
			sb.append("{");
			sb.append("id:" + group.getId());
			sb.append(",text:\"" + group.getName() + "\"");
			if (index != list.size()) {
				sb.append("},");
			} else {
				sb.append("}");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 添加管理组
	 */
	@RequestMapping(value = "/managerGroup_doSave", method = RequestMethod.POST)
	public String managerGroup_doSave(HttpServletRequest request, HttpServletResponse response, ManagerGroup managerGroup) {
		String message = "";
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			if (hasOperate(request)) {
				// ======================业务逻辑====================
				if (managerGroupService.selectManagerGroupByName(managerGroup.getName()) != null) {
					message = "管理组已存在!";
				} else {
					managerGroup.setAddTime(new Date());
					managerGroupService.insertManagerGroup(managerGroup);
					String[] operateids = request.getParameter("operateIds").trim().split(",");
					managerGroupService.updateManagerGroup(managerGroup, null, operateids);
					message = "添加成功!";
				}
				// ======================业务逻辑====================
			} else {
				message = "权限不足!";
			}
		} catch (Exception e) {
			message = e.getMessage();
			e.printStackTrace();
		}
		out.print(message);
		return null;
	}

	/**
	 * 查询管理组列表（页面中的表格）
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/managerGroup_doSelect")
	public String managerGroup_doSelect(HttpServletRequest request, HttpServletResponse response, Pageutil pageUtil) {
		try {
			if (hasOperate(request)) {
				// ======================业务逻辑====================
				response.setContentType("text/html;charset=UTF-8");
				List<ManagerGroup> list = managerGroupService.selectAllManagerGroup();
				// 将对象集合转换为字符串集合
				List dataAll = managerGroupList2StringList(list);
				HashMap result = new HashMap();
				result.put("data", MapUtil.fomatData(dataAll));
				result.put("total", list.size());
				// 将数据json化
				String json = JSON.Encode(result);
				response.getWriter().write(json);

				// ======================业务逻辑====================
			} else {
				// request.setAttribute(BaseString.KEY_ERROR, BaseString.INFO_NO_OPERATE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将管理组数据集合转换为字符串形式
	 * 
	 * @param userList
	 *            要转换的用户数据集合
	 * @return 转换后的字符串集合
	 */
	public ArrayList<String> managerGroupList2StringList(List<ManagerGroup> managerGroupList) {
		ArrayList<String> strList = new ArrayList<String>();
		for (ManagerGroup group : managerGroupList) {
			StringBuffer sb = new StringBuffer();
			sb.append("id=" + group.getId());
			sb.append(",name=" + group.getName());
			sb.append(",createDatetime=" + DateTimeUtils.format(group.getAddTime(), DateTimeUtils.YEAR_MONTH_DAY));
			sb.append(",operateGroupSize=" + group.getOperateGroups().size());
			strList.add(sb.toString());
		}
		return strList;
	}

	/**
	 * 进入管理组编辑页面
	 * 
	 */
	@RequestMapping(value = "/managerGroup_toUpdate", method = RequestMethod.GET)
	public String managerGroup_toUpdate_get(HttpServletRequest request, ManagerGroup managerGroup, Model model) {
		try {
			if (hasOperate(request)) {
				// ======================业务逻辑====================
				String id = request.getParameter("id");
				// 根据分组编号获取分组
				managerGroup = managerGroupService.selectManagerGroupById(Integer.parseInt(id));
				// 获取所有权限（用于页面显示checkBox）
				List<Operate> operateList = OperateManage.getOperaMenuDataList();
				List<OperateGroup> operategroupList = managerGroup.getOperateGroups();
				// 获取该分组的权限id，放入groupmanagerids，让如request，用于页面获取权限默认值
				List<Integer> operateids = new ArrayList<Integer>();
				for (int i = 0,size=operategroupList.size(); i <size ; i++) {
					OperateGroup group = operategroupList.get(i);
					if(OperateManage.getOperaMenuDataListById(group.getOperateId()).getPid()!=-1){
						operateids.add(operategroupList.get(i).getOperateId());
					}
					if(OperateManage.getOperaMenuDataListByPid(group.getOperateId()).size()==0){
						operateids.add(operategroupList.get(i).getOperateId());
					}
				}
				model.addAttribute("managerGroup", managerGroup);
				model.addAttribute("operateList", operateList);
				model.addAttribute("operateids", operateids);
				return "jsp/operate/update/managerGroup";
				// ======================业务逻辑====================
			} else {
				request.setAttribute(BaseString.KEY_ERROR, BaseString.INFO_NO_OPERATE);
				return ToPage.HT_INFO;
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(BaseString.KEY_EXCEPTION, e);
			return ToPage.HT_INFO;
		}
	}

	/**
	 * 更新管理组
	 */
	@RequestMapping(value = "/managerGroup_doUpdate", method = RequestMethod.POST)
	public String managerGroup_toUpdate_post(HttpServletRequest request, HttpServletResponse response, ManagerGroup managerGroup) {
		String message = "";
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			if (hasOperate(request)) {
				// ======================业务逻辑====================
				String[] operateids = request.getParameter("operateIds").trim().split(",");
				// 以前拥有的权限
				List<Operate> oldOperateList = operateService.selectOperateByManagerGroupeId(managerGroup.getId());
				managerGroupService.updateManagerGroup(managerGroup, oldOperateList, operateids);
				message = "修改成功!";
				// ======================业务逻辑====================
			} else {
				message = "权限不足!";
			}
		} catch (Exception e) {
			message = e.getMessage();
			e.printStackTrace();
		}
		out.print(message);
		return null;
	}

	/**
	 * 删除管理组
	 */
	@RequestMapping(value = "/managerGroup_doDelete", method = RequestMethod.GET)
	public String managerGroup_doDelete_doGet(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		String message = "";
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			if (hasOperate(request)) {
				// ======================业务逻辑====================
				managerGroupService.deleteManagerGroup(Integer.parseInt(request.getParameter("id")));
				message = "删除成功！";
				// ======================业务逻辑====================
			} else {
				message = "权限不足！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
		} finally {
			out.print(message);
			out.close();
		}
		return null;
	}
	
	/**
	 * 是否拥有权限
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean hasOperate(HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Integer> operateArray = (List<Integer>) session.getAttribute("operateList");
		if (operateArray.contains(Operate.MANAGE_GROUP_MANAGE)) {
			return true;
		} else {
			return false;
		}
	}
}
