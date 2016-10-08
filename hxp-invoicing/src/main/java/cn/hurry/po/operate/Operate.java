package cn.hurry.po.operate;

import javax.swing.SizeRequirements;

/**
 * 权限菜单数据实例对象
 * 
 * @author ZhouHao
 * 
 */
@SuppressWarnings("serial")
public class Operate extends SizeRequirements implements Comparable<Operate> {
	
	/**
	 * 收银管理
	 */
	public static int CHECKOUT_MANAGE = 51;

	/**
	 * 收银管理
	 */
	public static int CHECKOUT_MANAGE_ = 50;

	/**
	 * 采购管理
	 */
	public static int BUY_MANAGE = 100;

	/**
	 * 采购单管理
	 */
	public static int BUY_ORDER_MANAGE = 110;

	/**
	 * 采购单审核
	 */
	public static int BUY_ORDER_CHECK_MANAGE = 120;

	/**
	 * 销售管理
	 */
	public static int SELL_MANAGE = 200;

	/**
	 * 销售单管理
	 */
	public static int SELL_ORDER_MANAGE = 210;

	/**
	 * 销售单审核
	 */
	public static int SELL_ORDER_CHECK_MANAGE = 220;

	/**
	 * 盘点管理
	 */
	public static int INVENTORY_MANAGE = 300;

	/**
	 * 库存盘点
	 */
	public static int INVENTORY_GOODS_MANAGE = 310;

	/**
	 * 损耗
	 */
	public static int LOSS_MANAGE = 400;

	/**
	 * 库存损耗
	 */
	public static int LOSS_GOODS_MANAGE = 410;

	/**
	 * 仓库管理
	 */
	public static int STORE_MANAGE = 810;

	/**
	 * 规格管理
	 */
	public static int NORMS_MANAGE = 820;

	/**
	 * 单位设置
	 */
	public static int UNIT_MANAGE = 830;

	/**
	 * 商品管理
	 */
	public static int GOODS_MANAGE = 850;

	/**
	 * 角色管理
	 */
	public static int USER_MANAGE = 910;

	/**
	 * 成本方案设置
	 */
	public static int SELLPLAN_MANAGE = 930;

	/**
	 * 角色管理
	 */
	public static int MANAGE_GROUP_MANAGE = 920;

	/**
	 * 权限菜单数据编号
	 */
	private int id;

	/**
	 * 权限菜单数据父级编号 -1为顶级
	 */
	private int pid;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 操作类型介绍
	 */
	private String text;

	/**
	 * 图标样式名称
	 */
	private String iconCls;

	/**
	 * 图标图片
	 */
	private String iconImg;

	/**
	 * 窗口标题
	 */
	private String title;

	/**
	 * 指向的连接
	 */
	private String url;

	private int index;

	private String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconImg() {
		return iconImg;
	}

	public void setIconImg(String iconImg) {
		this.iconImg = iconImg;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int compareTo(Operate o) {
		int did = o.getId();
		if (id < did) {
			return -1;
		} else if (id > did) {
			return 1;
		} else {
			return 0;
		}
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("name:" + "\"" + this.getName() + "\",");
		sb.append("type:" + "\"ux.iframewindow\",");
		sb.append("text:" + "\"" + this.getText() + "\",");
		sb.append("title:" + "\"" + this.getTitle() + "\",");
		sb.append("url:" + "\"" + this.getUrl() + "?id=" + this.getId() + "\",");
		sb.append("iconCls:" + "\"" + this.getIconCls() + "\"");
		return sb.toString();
	}

}
