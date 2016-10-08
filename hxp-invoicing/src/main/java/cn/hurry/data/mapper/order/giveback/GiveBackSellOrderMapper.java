package cn.hurry.data.mapper.order.giveback;

import java.util.HashMap;
import java.util.List;

import cn.hurry.po.order.Order;
import cn.hurry.po.order.giveback.GiveBackSellOrder;

/**
 * 销售退货单映射接口
 * 
 * @author ZhouHao
 * 
 */
public interface GiveBackSellOrderMapper {
	/**
	 * 添加销售退货单
	 * 
	 * @param giveBackSellOrder
	 *            销售退货单实例
	 * @return 成功添加数量
	 * @throws Exception
	 *             添加失败异常
	 */
	public int insertGiveBackSellOrder(Order order) throws Exception;

	/**
	 * 修改销售退货单
	 * 
	 * @param GiveBackSellOrder
	 *            销售退货单实例
	 * @return 成功修改数量
	 * @throws Exception
	 *             修改失败异常
	 */
	public int updateGiveBackSellOrder(Order order) throws Exception;

	/**
	 * 删除销售退货单
	 * 
	 * @param order
	 *            销售退货单实例
	 * @return 成功删除数量
	 * @throws Exception
	 *             删除失败异常
	 */
	public int deleteGiveBackSellOrder(Order order) throws Exception;

	/**
	 * 根据销售退货单编号查询销售退货单
	 * 
	 * @param id
	 *            销售退货单编号
	 * @return 销售退货单实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public GiveBackSellOrder selectGiveBackSellOrderById(String id) throws Exception;

	/**
	 * 根据map条件集合查询销售退货单
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的销售退货单集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<GiveBackSellOrder> selectGiveBackSellOrderByMap(HashMap<String, Object> map) throws Exception;

	public int countGiveBackSellOrderByMap(HashMap<String, Object> map) throws Exception;
	
	public String selectMaxId() throws Exception;
}
