package cn.hurry.data.mapper.order.sell;

import java.util.List;
import java.util.Map;

import cn.hurry.po.order.OrderGoods;
import cn.hurry.po.order.sell.SellOrderGoods;

/**
 * 销售单商品绑定映射接口
 * 
 * @author ZhouHao
 * 
 */
public interface SellOrderGoodsMapper {
	/**
	 * 添加销售单商品绑定
	 * 
	 * @param SellOrderGoods
	 *            销售单商品绑定实例
	 * @return 成功添加数量
	 * @throws Exception
	 *             添加失败异常
	 */
	public int insertSellOrderGoods(OrderGoods orderGoods) throws Exception;

	/**
	 * 修改销售单商品绑定
	 * 
	 * @param SellOrderGoods
	 *            销售单商品绑定实例
	 * @return 成功修改数量
	 * @throws Exception
	 *             修改失败异常
	 */
	public int updateSellOrderGoods(OrderGoods orderGoods) throws Exception;

	/**
	 * 删除销售单商品绑定
	 * 
	 * @param SellOrderGoods
	 *            销售单商品绑定实例
	 * @return 成功删除数量
	 * @throws Exception
	 *             删除失败异常
	 */
	public int deleteSellOrderGoods(OrderGoods orderGoods) throws Exception;

	/**
	 * 根据销售单商品绑定编号查询销售单商品绑定
	 * 
	 * @param id
	 *            销售单商品绑定编号
	 * @return 销售单商品绑定实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public SellOrderGoods selectSellOrderGoodsById(int id) throws Exception;
	
	public List<SellOrderGoods> selectSellOrderGoodsByOrderId(String id) throws Exception;

	/**
	 * 根据map条件集合查询销售单商品绑定
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的销售单商品绑定集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<SellOrderGoods> selectSellOrderGoodsByMap(Map<String, Object> map) throws Exception;

	public int countSellOrderGoodsByMap(Map<String, Object> map) throws Exception;
}
