package cn.hurry.po.loss;

import java.util.Date;

import cn.hurry.po.order.OrderGoods;
import cn.hurry.po.user.User;
import cn.hurry.util.DateTimeUtils;

/**
 * 商品损耗
 * 
 * @author ZhouHao
 * 
 */
public class Loss {
	/**
	 * 损耗编号
	 */
	private int id;

	/**
	 * 损耗对应的单据商品编号
	 */
	private int orderGoodsId;

	/**
	 * 损耗对应的单据商品
	 */
	private OrderGoods orderGoods;

	private Date createDate;
	/**
	 * 损耗数量
	 */
	private double number;

	/**
	 * 备注
	 */
	private String remark;

	private int userId;

	private User user;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderGoodsId() {
		return orderGoodsId;
	}

	public void setOrderGoodsId(int orderGoodsId) {
		this.orderGoodsId = orderGoodsId;
	}

	public OrderGoods getOrderGoods() {
		return orderGoods;
	}

	public void setOrderGoods(OrderGoods orderGoods) {
		this.orderGoods = orderGoods;
	}

	public double getNumber() {
		return number;
	}

	public void setNumber(double number) {
		this.number = number;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("lid=" + id);
		builder.append("," + orderGoods.toString());
		builder.append(",remark=" + remark);
		builder.append(",lossnumber=" + number);
		builder.append(",luser=" + (user!=null?user.getUsername():""));
		builder.append(",createDate=" + (DateTimeUtils.format(createDate, DateTimeUtils.YEAR_MONTH_DAY)));
		return builder.toString();
	}

}
