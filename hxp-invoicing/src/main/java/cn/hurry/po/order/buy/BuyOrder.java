package cn.hurry.po.order.buy;

import cn.hurry.po.order.Order;

/**
 * 采购单
 * 
 * @author ZhouHao
 * 
 */
public class BuyOrder extends Order {

	
	public byte getType() {
		return Order.TYPE_BUY_ORDER;
	}
}
