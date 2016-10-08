package cn.hurry.po.order.giveback;

import cn.hurry.po.order.Order;

/**
 * 采购退货单
 * @author ZhouHao
 *
 */
public class GiveBackBuyOrder extends Order {
	@Override
	public byte getType() {
		return Order.TYPE_BUY_RETURN_ORDER;
	}
}
