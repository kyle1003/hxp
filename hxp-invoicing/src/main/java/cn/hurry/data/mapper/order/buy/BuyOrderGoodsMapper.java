package cn.hurry.data.mapper.order.buy;

import java.util.List;
import java.util.Map;

import cn.hurry.po.order.OrderGoods;
import cn.hurry.po.order.buy.BuyOrderGoods;

/**
 * 进货单商品绑定映射接口
 * 
 * @author ZhouHao
 * 
 */
public interface BuyOrderGoodsMapper {
	/**
	 * 添加进货单商品绑定
	 * 
	 * @param BuyOrderGoods
	 *            进货单商品绑定实例
	 * @return 成功添加数量
	 * @throws Exception
	 *             添加失败异常
	 */
	public int insertBuyOrderGoods(OrderGoods buyOrderGoods) throws Exception;

	/**
	 * 修改进货单商品绑定
	 * 
	 * @param BuyOrderGoods
	 *            进货单商品绑定实例
	 * @return 成功修改数量
	 * @throws Exception
	 *             修改失败异常
	 */
	public int updateBuyOrderGoods(OrderGoods buyOrderGoods) throws Exception;

	/**
	 * 删除进货单商品绑定
	 * 
	 * @param BuyOrderGoods
	 *            进货单商品绑定实例
	 * @return 成功删除数量
	 * @throws Exception
	 *             删除失败异常
	 */
	public int deleteBuyOrderGoods(OrderGoods buyOrderGoods) throws Exception;

	/**
	 * 根据进货单商品绑定编号查询进货单商品绑定
	 * 
	 * @param id
	 *            进货单商品绑定编号
	 * @return 进货单商品绑定实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public BuyOrderGoods selectBuyOrderGoodsById(int id) throws Exception;

	public List<BuyOrderGoods> selectBuyOrderGoodsByOrderId(String id) throws Exception;

	/**
	 * 根据map条件集合查询进货单商品绑定
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的进货单商品绑定集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<BuyOrderGoods> selectBuyOrderGoodsByMap(Map<String, Object> map) throws Exception;
	
	public int countBuyOrderGoodsByMap(Map<String, Object> map) throws Exception;
}
