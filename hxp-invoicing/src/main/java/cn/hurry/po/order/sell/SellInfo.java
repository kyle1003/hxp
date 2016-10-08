package cn.hurry.po.order.sell;

import cn.hurry.po.order.OrderGoods;
import cn.hurry.po.order.buy.BuyOrder;

/**
 * 销售详情
 * 
 * @author ZhouHao
 * 
 */
public class SellInfo {
	private int id;

	/**
	 * 销售单编号
	 */
	private String sellOrderId;

	/**
	 * 采购单编号
	 */
	private String buyOrderId;

	/**
	 * 采购商品编号
	 */
	private int buyOrderGoodsId;

	/**
	 * 销售商品编号
	 */
	private int sellOrderGoodsId;

	/**
	 * 本次消耗数量
	 */
	private double number;

	/**
	 * 本次剩余数量
	 */
	private double surplusNumber;
	/**
	 * 销售单实例
	 */
	private SellOrder sellOrder;

	/**
	 * 采购单实例
	 */
	private BuyOrder buyOrder;

	/**
	 * 订单商品实例
	 */
	private OrderGoods sellOrderGoods;

	/**
	 * 订单商品实例
	 */
	private OrderGoods buyOrderGoods;

	private String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSellOrderId() {
		return sellOrderId;
	}

	public void setSellOrderId(String sellOrderId) {
		this.sellOrderId = sellOrderId;
	}

	public String getBuyOrderId() {
		return buyOrderId;
	}

	public void setBuyOrderId(String buyOrderId) {
		this.buyOrderId = buyOrderId;
	}

	public int getBuyOrderGoodsId() {
		return buyOrderGoodsId;
	}

	public void setBuyOrderGoodsId(int buyOrderGoodsId) {
		this.buyOrderGoodsId = buyOrderGoodsId;
	}

	public int getSellOrderGoodsId() {
		return sellOrderGoodsId;
	}

	public void setSellOrderGoodsId(int sellOrderGoodsId) {
		this.sellOrderGoodsId = sellOrderGoodsId;
	}

	public SellOrder getSellOrder() {
		return sellOrder;
	}

	public void setSellOrder(SellOrder sellOrder) {
		this.sellOrder = sellOrder;
	}

	public BuyOrder getBuyOrder() {
		return buyOrder;
	}

	public void setBuyOrder(BuyOrder buyOrder) {
		this.buyOrder = buyOrder;
	}

	public OrderGoods getSellOrderGoods() {
		return sellOrderGoods;
	}

	public void setSellOrderGoods(OrderGoods sellOrderGoods) {
		this.sellOrderGoods = sellOrderGoods;
	}

	public OrderGoods getBuyOrderGoods() {
		return buyOrderGoods;
	}

	public void setBuyOrderGoods(OrderGoods buyOrderGoods) {
		this.buyOrderGoods = buyOrderGoods;
	}

	public double getNumber() {
		return number;
	}

	public void setNumber(double number) {
		this.number = number;
	}

	public double getSurplusNumber() {
		return surplusNumber;
	}

	public void setSurplusNumber(double surplusNumber) {
		this.surplusNumber = surplusNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
