package cn.hurry.po.order.giveback;

import java.util.Date;

import cn.hurry.po.order.sell.SellOrderGoods;
import cn.hurry.po.user.User;
import cn.hurry.util.DateTimeUtils;

/**
 * 退货信息
 * 
 * @author ZhouHao
 * 
 */
public class GiveBackInfo {
	private int id;

	private int sellOrderGoodsId;

	private SellOrderGoods sellOrderGoods;

	private double number;

	private Date backTime;

	private int createUserId;

	private User createUser;

	private String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSellOrderGoodsId() {
		return sellOrderGoodsId;
	}

	public void setSellOrderGoodsId(int sellOrderGoodsId) {
		this.sellOrderGoodsId = sellOrderGoodsId;
	}

	public SellOrderGoods getSellOrderGoods() {
		return sellOrderGoods;
	}

	public void setSellOrderGoods(SellOrderGoods sellOrderGoods) {
		this.sellOrderGoods = sellOrderGoods;
	}

	public double getNumber() {
		return number;
	}

	public void setNumber(double number) {
		this.number = number;
	}

	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
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
		builder.append("id=" + id);
		builder.append(",goodsName=" + (sellOrderGoods.getGoods().getName()));
		builder.append(",orderId=" + (sellOrderGoods.getOrder().getId()));
		builder.append(",number=" + getNumber());
		builder.append(",remark=" + (getRemark()));
		builder.append(",user=" + (getCreateUser() != null ? getCreateUser().getUsername() : ""));
		builder.append(",backTime=" + DateTimeUtils.format(getBackTime(), DateTimeUtils.YEAR_MONTH_DAY_HOUR_MINUTE));
		return builder.toString();
	}
}
