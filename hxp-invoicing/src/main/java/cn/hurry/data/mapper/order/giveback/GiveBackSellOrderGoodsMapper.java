package cn.hurry.data.mapper.order.giveback;

import java.util.HashMap;
import java.util.List;

import cn.hurry.po.order.OrderGoods;
import cn.hurry.po.order.giveback.GiveBackSellOrderGoods;

/**
 * 销售退货单商品绑定商品绑定映射接口
 * 
 * @author ZhouHao
 * 
 */
public interface GiveBackSellOrderGoodsMapper {
	/**
	 * 添加销售退货单商品绑定商品绑定
	 * 
	 * @param giveBackBuyOrderGoods
	 *            销售退货单商品绑定商品绑定实例
	 * @return 成功添加数量
	 * @throws Exception
	 *             添加失败异常
	 */
	public int insertGiveBackSellOrderGoods(OrderGoods orderGoods) throws Exception;

	/**
	 * 修改销售退货单商品绑定商品绑定
	 * 
	 * @param giveBackBuyOrderGoods
	 *            销售退货单商品绑定商品绑定实例
	 * @return 成功修改数量
	 * @throws Exception
	 *             修改失败异常
	 */
	public int updateGiveBackSellOrderGoods(OrderGoods orderGoods) throws Exception;

	/**
	 * 删除销售退货单商品绑定商品绑定
	 * 
	 * @param giveBackBuyOrderGoods
	 *            销售退货单商品绑定商品绑定实例
	 * @return 成功删除数量
	 * @throws Exception
	 *             删除失败异常
	 */
	public int deleteGiveBackSellOrderGoods(OrderGoods orderGoods) throws Exception;

	/**
	 * 根据销售退货单商品绑定商品绑定编号查询销售退货单商品绑定商品绑定
	 * 
	 * @param id
	 *            销售退货单商品绑定商品绑定编号
	 * @return 销售退货单商品绑定商品绑定实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public GiveBackSellOrderGoods selectGiveBackSellOrderGoodsById(int id) throws Exception;

	public List<GiveBackSellOrderGoods> selectGiveBackSellOrderGoodsByOrderId(int id) throws Exception;

	/**
	 * 根据map条件集合查询销售退货单商品绑定商品绑定
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的销售退货单商品绑定商品绑定集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<GiveBackSellOrderGoods> selectGiveBackSellOrderGoodsByMap(HashMap<String, Object> map) throws Exception;

	public int countGiveBackSellOrderGoodsByMap(HashMap<String, Object> map) throws Exception;
}
