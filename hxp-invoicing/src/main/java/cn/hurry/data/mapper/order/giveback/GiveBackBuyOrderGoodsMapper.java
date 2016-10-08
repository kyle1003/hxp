package cn.hurry.data.mapper.order.giveback;

import java.util.HashMap;
import java.util.List;

import cn.hurry.po.order.OrderGoods;
import cn.hurry.po.order.giveback.GiveBackBuyOrderGoods;

/**
 * 采购退货单商品绑定商品绑定映射接口
 * 
 * @author ZhouHao
 * 
 */
public interface GiveBackBuyOrderGoodsMapper {
	/**
	 * 添加采购退货单商品绑定商品绑定
	 * 
	 * @param giveBackBuyOrderGoods
	 *            采购退货单商品绑定商品绑定实例
	 * @return 成功添加数量
	 * @throws Exception
	 *             添加失败异常
	 */
	public int insertGiveBackBuyOrderGoods(OrderGoods orderGoods) throws Exception;

	/**
	 * 修改采购退货单商品绑定商品绑定
	 * 
	 * @param giveBackBuyOrderGoods
	 *            采购退货单商品绑定商品绑定实例
	 * @return 成功修改数量
	 * @throws Exception
	 *             修改失败异常
	 */
	public int updateGiveBackBuyOrderGoods(OrderGoods orderGoods) throws Exception;

	/**
	 * 删除采购退货单商品绑定商品绑定
	 * 
	 * @param giveBackBuyOrderGoods
	 *            采购退货单商品绑定商品绑定实例
	 * @return 成功删除数量
	 * @throws Exception
	 *             删除失败异常
	 */
	public int deleteGiveBackBuyOrderGoods(OrderGoods orderGoods) throws Exception;

	/**
	 * 根据采购退货单商品绑定商品绑定编号查询采购退货单商品绑定商品绑定
	 * 
	 * @param id
	 *            采购退货单商品绑定商品绑定编号
	 * @return 采购退货单商品绑定商品绑定实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public GiveBackBuyOrderGoods selectGiveBackBuyOrderGoodsById(int id) throws Exception;

	public List<GiveBackBuyOrderGoods> selectGiveBackBuyOrderByOrderId(String id) throws Exception;

	/**
	 * 根据map条件集合查询采购退货单商品绑定商品绑定
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的采购退货单商品绑定商品绑定集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<GiveBackBuyOrderGoods> selectGiveBackBuyOrderGoodsByMap(HashMap<String, Object> map) throws Exception;

	public int countGiveBackBuyOrderGoodsByMap(HashMap<String, Object> map) throws Exception;
}
