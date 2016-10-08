package cn.hurry.po.goods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.hurry.po.loss.Loss;
import cn.hurry.po.order.buy.BuyOrder;
import cn.hurry.po.order.sell.SellInfo;
import cn.hurry.po.order.sell.SellOrder;
import cn.hurry.po.order.sell.SellOrderGoods;

/**
 * 
 * @author ZhouHao
 * 
 */
public class InvoicingInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2546969910315148843L;

	/**
	 * 商品对象
	 */
	private Goods goods;

	/**
	 * 该商品所有采购单
	 */
	private List<BuyOrder> buyOrders;

	/**
	 * 该商品所有销售单
	 */
	private List<SellOrder> sellOrders;

	private List<SellInfo> sellInfos;
	
	/**
	 * 该商品所有损耗信息
	 */
	private List<Loss> losses;

	/**
	 * 销售单和进货单以及损耗的绑定集合 &lt;采购单编号,[List&lt;SellOrder&gt;,List&lt;Loss&gt;]&gt;
	 */
	private Map<BuyOrder, Object[]> buyOrderBindOfSL;

	public List<SellInfo> getSellInfos() {
		return sellInfos;
	}

	public void setSellInfos(List<SellInfo> sellInfos) {
		this.sellInfos = sellInfos;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public List<BuyOrder> getBuyOrders() {
		return buyOrders;
	}

	public void setBuyOrders(List<BuyOrder> buyOrders) {
		this.buyOrders = buyOrders;
	}

	public List<SellOrder> getSellOrders() {
		return sellOrders;
	}

	public void setSellOrders(List<SellOrder> sellOrders) {
		this.sellOrders = sellOrders;
	}

	public List<Loss> getLosses() {
		return losses;
	}

	public void setLosses(List<Loss> losses) {
		this.losses = losses;
	}

	public Map<BuyOrder, Object[]> getBuyOrderBindOfSL() {
		if (buyOrderBindOfSL == null) {
			buyOrderBindOfSL = new LinkedHashMap<BuyOrder, Object[]>();
			//遍历采购单
			for (BuyOrder order:getBuyOrders()) {
				List<SellOrderGoods> sellOrderGoods = new ArrayList<SellOrderGoods>();
				List<Loss> losses = new ArrayList<Loss>();
				//遍历销售详情
				for(SellInfo sellInfo:getSellInfos()){
					//销售详情符合采购单
					if(order.getId().equals(sellInfo.getBuyOrderId())){
						sellOrderGoods.add((SellOrderGoods) sellInfo.getSellOrderGoods()) ;
					}
				}
				for(Loss loss:getLosses()){
					if(loss.getOrderGoods().getOrderId().equals(order.getId())){
						losses.add(loss);
					}
				}
				buyOrderBindOfSL.put(order, new Object[]{sellOrderGoods,losses});
			}
		}
		return buyOrderBindOfSL;
	}

	public void setBuyOrderBindOfSL(Map<BuyOrder, Object[]> buyOrderBindOfSL) {
		this.buyOrderBindOfSL = buyOrderBindOfSL;
	}

}
