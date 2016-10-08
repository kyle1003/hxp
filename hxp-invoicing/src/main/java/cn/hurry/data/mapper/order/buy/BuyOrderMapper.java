package cn.hurry.data.mapper.order.buy;

import java.util.List;
import java.util.Map;

import cn.hurry.po.order.Order;
import cn.hurry.po.order.buy.BuyOrder;

/**
 * 进货单映射接口
 * 
 * @author ZhouHao
 * 
 */
public interface BuyOrderMapper {
	/**
	 * 添加进货单
	 * 
	 * @param BuyOrder
	 *            进货单实例
	 * @return 成功添加数量
	 * @throws Exception
	 *             添加失败异常
	 */
	public int insertBuyOrder(Order order) throws Exception;

	/**
	 * 修改进货单
	 * 
	 * @param BuyOrder
	 *            进货单实例
	 * @return 成功修改数量
	 * @throws Exception
	 *             修改失败异常
	 */
	public int updateBuyOrder(Order order) throws Exception;
	
	
	/**
	 * 删除进货单
	 * 
	 * @param BuyOrder
	 *            进货单实例
	 * @return 成功删除数量
	 * @throws Exception
	 *             删除失败异常
	 */
	public int deleteBuyOrder(Order order) throws Exception;

	/**
	 * 根据进货单编号查询进货单
	 * 
	 * @param id
	 *            进货单编号
	 * @return 进货单实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public BuyOrder selectBuyOrderById(String id) throws Exception;

	/**
	 * 根据map条件集合查询进货单
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的进货单集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<BuyOrder> selectBuyOrderByMap(Map<String, Object> map) throws Exception;

	public int countBuyOrderByMap(Map<String, Object> map) throws Exception;
	
	public double sumBuyOrderByMap(Map<String, Object> map) throws Exception;
	
	public String selectMaxId() throws Exception;
}
