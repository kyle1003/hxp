package cn.hurry.po.order.sell;

import cn.hurry.po.order.Order;

/**
 * 销售单
 * 
 * @author ZhouHao
 * 
 */
public class SellOrder extends Order {
	public byte getType() {
		return Order.TYPE_SELL_ORDER;
	}
}
