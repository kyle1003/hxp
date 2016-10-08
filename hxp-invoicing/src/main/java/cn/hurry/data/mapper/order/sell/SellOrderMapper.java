package cn.hurry.data.mapper.order.sell;

import java.util.List;
import java.util.Map;

import cn.hurry.po.order.Order;
import cn.hurry.po.order.sell.SellOrder;

/**
 * 销售单映射接口
 * 
 * @author ZhouHao
 * 
 */
public interface SellOrderMapper {
	/**
	 * 添加销售单
	 * 
	 * @param SellOrder
	 *            销售单实例
	 * @return 成功添加数量
	 * @throws Exception
	 *             添加失败异常
	 */
	public int insertSellOrder(Order order) throws Exception;

	/**
	 * 修改销售单
	 * 
	 * @param SellOrder
	 *            销售单实例
	 * @return 成功修改数量
	 * @throws Exception
	 *             修改失败异常
	 */
	public int updateSellOrder(Order order) throws Exception;

	/**
	 * 删除销售单
	 * 
	 * @param SellOrder
	 *            销售单实例
	 * @return 成功删除数量
	 * @throws Exception
	 *             删除失败异常
	 */
	public int deleteSellOrder(Order order) throws Exception;

	/**
	 * 根据销售单编号查询销售单
	 * 
	 * @param id
	 *            销售单编号
	 * @return 销售单实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public SellOrder selectSellOrderById(String id) throws Exception;

	/**
	 * 根据map条件集合查询销售单
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的销售单集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<SellOrder> selectSellOrderByMap(Map<String, Object> map) throws Exception;

	public int countSellOrderByMap(Map<String, Object> map) throws Exception;
	
	public double sumSellOrderByMap(Map<String, Object> map) throws Exception;
	
	
	public String selectMaxId() throws Exception;
}
