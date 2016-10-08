package cn.hurry.po.inventory;

import java.util.Date;
import java.util.List;

import cn.hurry.manage.store.StoreManage;
import cn.hurry.po.store.Store;
import cn.hurry.po.user.User;
import cn.hurry.util.DateTimeUtils;

/**
 * 盘点
 * 
 * @author ZhouHao
 * 
 */
public class Inventory {
	public static final String ID_KEY = "PDyyyyMMdd{5}";

	public static final byte STATUS_NOT_CHECKED = 0;

	public static final byte STATUS_CHECKED = 1;
	/**
	 * 盘点编号
	 */
	private int id;

	/**
	 * 盘点号
	 */
	private String code;

	/**
	 * 盘点日期
	 */
	private Date createDate;

	/**
	 * 盘点人
	 */
	private int userId;

	/**
	 * 仓库编号
	 */
	private int storeId;

	/**
	 * 仓库实例
	 */
	private Store store;

	private User user;
	/**
	 * 状态
	 */
	private byte status;

	List<InventoryGoods> inventoryGoods;

	public byte getStatus() {
		return status;
	}

	public List<InventoryGoods> getInventoryGoods() {
		return inventoryGoods;
	}

	public void setInventoryGoods(List<InventoryGoods> inventoryGoods) {
		this.inventoryGoods = inventoryGoods;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("id=" + id);
		builder.append(",code=" + code);
		builder.append(",createDate=" + DateTimeUtils.format(createDate, DateTimeUtils.YEAR_MONTH_DAY));
		builder.append(",userId=" + getUser().getUsername());
		builder.append(",storeId=" + (StoreManage.getStoreById(storeId)).getName());
		builder.append(",status=" + (status == STATUS_CHECKED ? "已结转" : "未结转"));
		return builder.toString();
	}
}
