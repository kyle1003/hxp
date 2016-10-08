package cn.hurry.data.mapper.order.giveback;

import java.util.HashMap;
import java.util.List;

import cn.hurry.po.order.Order;
import cn.hurry.po.order.giveback.GiveBackBuyOrder;

/**
 * 采购退货单映射接口
 * 
 * @author ZhouHao
 * 
 */
public interface GiveBackBuyOrderMapper {
	/**
	 * 添加采购退货单
	 * 
	 * @param order
	 *            采购退货单实例
	 * @return 成功添加数量
	 * @throws Exception
	 *             添加失败异常
	 */
	public int insertGiveBackBuyOrder(Order order) throws Exception;

	/**
	 * 修改采购退货单
	 * 
	 * @param order
	 *            采购退货单实例
	 * @return 成功修改数量
	 * @throws Exception
	 *             修改失败异常
	 */
	public int updateGiveBackBuyOrder(Order order) throws Exception;

	/**
	 * 删除采购退货单
	 * 
	 * @param order
	 *            采购退货单实例
	 * @return 成功删除数量
	 * @throws Exception
	 *             删除失败异常
	 */
	public int deleteGiveBackBuyOrder(Order order) throws Exception;

	/**
	 * 根据采购退货单编号查询采购退货单
	 * 
	 * @param id
	 *            采购退货单编号
	 * @return 采购退货单实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public GiveBackBuyOrder selectGiveBackBuyOrderById(String id) throws Exception;

	/**
	 * 根据map条件集合查询采购退货单
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的采购退货单集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<GiveBackBuyOrder> selectGiveBackBuyOrderByMap(HashMap<String, Object> map) throws Exception;

	public int countGiveBackBuyOrderByMap(HashMap<String, Object> map) throws Exception;

	public String selectMaxId() throws Exception;
}
