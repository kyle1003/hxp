package cn.hurry.manage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import cn.hurry.po.operate.Operate;
import cn.hurry.service.operate.OperateService;

public class OperateManage {
	private static final HashMap<Integer, Operate> OPERA_MANAGE = new HashMap<Integer, Operate>();

	static {
		loadOperaMenuData();
	}

	/**
	 * 重新加载权限
	 */
	public static void reloadOperaMenuData() {
		synchronized (OPERA_MANAGE) {
			for (Operate operate : getOperaMenuDataList()) {
				OPERA_MANAGE.remove(operate.getId());
			}
			loadOperaMenuData();
		}

	}

	/**
	 * 加载权限
	 */
	private static void loadOperaMenuData() {
		synchronized (OPERA_MANAGE) {
			try {
				OperateService operaMenuDataService = new OperateService();
				ArrayList<Operate> operaMenuDataList = operaMenuDataService.selectAllOperaMenuData();
				for (Operate operaMenuData : operaMenuDataList) {
					OPERA_MANAGE.put(operaMenuData.getId(), operaMenuData);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取权限菜单数据
	 * 
	 * @return
	 */
	public static ArrayList<Operate> getOperaMenuDataList() {
		synchronized (OPERA_MANAGE) {
			ArrayList<Operate> list = new ArrayList<Operate>(OPERA_MANAGE.values());
			Collections.sort(list);
			return list;
		}
	}

	public static Operate getOperaMenuDataListById(int id) {
		synchronized (OPERA_MANAGE) {
			for (Operate operate : getOperaMenuDataList()) {
				if (operate.getId() == id) {
					return operate;
				}
			}
			return null;
		}
	}

	/**
	 * 获取父级权限菜单数据
	 * 
	 * @param pid
	 *            父级权限菜单数据编号
	 * @param operaList
	 *            权限列表
	 * @return
	 */
	public static ArrayList<Operate> getOperaMenuDataListByPid(int pid, List<Integer> operaList) {
		synchronized (OPERA_MANAGE) {
			ArrayList<Operate> list = new ArrayList<Operate>();
			for (Operate data : getOperaMenuDataList()) {
				if (data.getPid() == pid && operaList.contains(data.getId())) {
					list.add(data);
				}
			}
			return list;
		}
	}

	/**
	 * 获取父级权限菜单数据
	 * 
	 * @param pid
	 *            父级权限菜单数据编号
	 * @param operaList
	 *            权限列表
	 * @return
	 */
	public static ArrayList<Operate> getOperaMenuDataListByPid(int pid) {
		synchronized (OPERA_MANAGE) {
			ArrayList<Operate> list = new ArrayList<Operate>();
			for (Operate data : getOperaMenuDataList()) {
				if (data.getPid() == pid) {
					list.add(data);
				}
			}
			return list;
		}
	}

	/**
	 * 权限数据 下拉列表
	 * 
	 * @param operaMenuDataList
	 * @return
	 */
	public static String OperaMenuDataList2Combobox(List<Operate> operaMenuDataList) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < operaMenuDataList.size(); i++) {
			Operate operaMenuData = operaMenuDataList.get(i);
			if (operaMenuData.getPid() == -1) {
				sb.append("{id: \"" + operaMenuData.getId() + "\",url:\""+operaMenuData.getUrl()+"\",text: \"" + operaMenuData.getName() + "\",\"folder\":1}");
			} else {
				sb.append("{id: \"" + operaMenuData.getId() + "\",url:\""+operaMenuData.getUrl()+"\",text: \"" + operaMenuData.getName() + "\", pid: \""
						+ operaMenuData.getPid() + "\",\"folder\":1}");
			}
			if (i != operaMenuDataList.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * 权限数据JSON（用于首页的图标显示）
	 * 
	 * @param operaMenuDataList
	 * @return
	 */
	public static String OperaMenuDataList2String(ArrayList<Operate> operaMenuDataList) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < operaMenuDataList.size(); i++) {
			Operate operaMenuData = operaMenuDataList.get(i);
			sb.append("{");
			sb.append("name:" + "\"" + operaMenuData.getName() + "\",");
			sb.append("type:" + "\"ux.iframewindow\",");
			sb.append("text:" + "\"" + operaMenuData.getText() + "\",");
			sb.append("title:" + "\"" + operaMenuData.getTitle() + "\",");
			sb.append("url:" + "\"" + operaMenuData.getUrl() + "?id=" + operaMenuData.getId() + "\",");
			sb.append("iconCls:" + "\"" + operaMenuData.getIconCls() + "\"");
			sb.append("}");
			if (i != operaMenuDataList.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}


	// public static void main(String[] args) {
	// System.out.println(OperaMenuDataList2String(getOperaMenuDataList()));
	// }
}
