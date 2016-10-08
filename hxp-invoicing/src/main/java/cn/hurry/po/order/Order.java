package cn.hurry.po.order;

import java.util.Date;
import java.util.List;

import cn.hurry.manage.store.StoreManage;
import cn.hurry.po.store.Store;
import cn.hurry.po.user.User;
import cn.hurry.service.user.UserService;
import cn.hurry.util.DateTimeUtils;
import cn.hurry.util.NumberUtil;

/**
 * 订单总类
 * 
 * @author ZhouHao
 * 
 */
public class Order {
	/**
	 * 采购单ID生成规则
	 */
	public static final String ID_KEY_BUY = "CGyyyyMMdd{6}";

	/**
	 * 销售单ID生成规则
	 */
	public static final String ID_KEY_SEL = "XSyyyyMMdd{6}";

	/**
	 * 销售退货单ID生成规则
	 */
	public static final String ID_KEY_SEL_RETURN = "XTyyyyMMdd{6}";

	/**
	 *采购退货单ID生成规则
	 */
	public static final String ID_KEY_BUY_RETURN = "CTyyyyMMdd{6}";

	/**
	 * 采购单
	 */
	public static final byte TYPE_BUY_ORDER = 1;

	/**
	 * 销售单
	 */
	public static final byte TYPE_SELL_ORDER = 2;

	/**
	 * 销售退货单
	 */
	public static final byte TYPE_SELL_RETURN_ORDER = 3;

	/**
	 * 采购退货单
	 */
	public static final byte TYPE_BUY_RETURN_ORDER = 4;

	/**
	 * 状态：未审核
	 */
	public static final byte STATUS_NOTCHECKED = 0;

	/**
	 * 状态：审核通过
	 */
	public static final byte STATUS_THROUGHCHECK = 1;

	/**
	 * 状态：未通过审核
	 */
	public static final byte STATUS_NO_THROUGHCHECK = -1;

	/**
	 * 状态：已删除
	 */
	public static final byte STATUS_DELETE = -2;

	/**
	 * 单号
	 */
	private String id;

	/**
	 * 仓库编号
	 */
	private int storeId;

	/**
	 * 仓库实例
	 */
	private Store store;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 订单日期
	 */
	private Date createDate;

	/**
	 * 操作员编号
	 */
	private int userId;

	/**
	 * 操作员实例
	 */
	private User user;

	/**
	 * 订单状态
	 */
	private byte status;

	/**
	 * 订单类型
	 */
	private byte type;

	/**
	 * 付款金额
	 */
	private double pay;

	/**
	 * 订单中的商品
	 */
	private List<OrderGoods> orderGoods;

	/**
	 * 支付方式
	 */
	private String payType;

	public List<OrderGoods> getOrderGoods() {
		return orderGoods;
	}

	public void setOrderGoods(List<OrderGoods> orderGoods) {
		this.orderGoods = orderGoods;
	}

	public double getPay() {
		return pay;
	}

	public void setPay(double pay) {
		this.pay = pay;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public User getUser() {
		if(user==null){
			try {
				user=new UserService().getUserById(userId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		Store store = StoreManage.getStoreById(getStoreId());
		buffer.append("id=" + id);
		buffer.append(",createDate=" + DateTimeUtils.format(createDate, DateTimeUtils.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND));
		buffer.append(",storeId=" + (store != null ? store.getName() : ""));
		buffer.append(",userId=" + (user != null ? user.getUsername() : ""));
		buffer.append(",pay=" + NumberUtil.convert(pay));
		buffer.append(",payType=" +(payType==null?"":payType));
		buffer.append(",type="
				+ (getType() == TYPE_BUY_ORDER ? "入库单" : getType() == TYPE_SELL_ORDER ? "销售单" : getType() == TYPE_BUY_RETURN_ORDER ? "采购退货单"
						: getType() == TYPE_SELL_RETURN_ORDER ? "销售退货单" : "*"));
		buffer.append(",status="
				+ (getStatus() == STATUS_DELETE ? "已删除" : getStatus() == STATUS_NOTCHECKED ? "未审核" : getStatus() == STATUS_NOTCHECKED ? "未通过审核"
						: getStatus() == STATUS_THROUGHCHECK ? "审核通过" : "*"));
		return buffer.toString();
	}

}
