package cn.hurry.po.order.giveback;

import cn.hurry.po.order.Order;

/**
 * 销售退货单
 * 
 * @author ZhouHao
 * 
 */
public class GiveBackSellOrder extends Order {
	@Override
	public byte getType() {
		return Order.TYPE_SELL_RETURN_ORDER;
	}
}
