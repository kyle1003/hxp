package cn.hurry.data.mapper.inventory;

import java.util.HashMap;
import java.util.List;

import cn.hurry.po.inventory.Inventory;

/**
 * 盘点映射接口
 * 
 * @author ZhouHao
 * 
 */
public interface InventoryMapper {
	/**
	 * 添加盘点
	 * 
	 * @param Inventory
	 *            盘点实例
	 * @return 成功添加数量
	 * @throws Exception
	 *             添加失败异常
	 */
	public int insertInventory(Inventory inventory) throws Exception;

	/**
	 * 修改盘点
	 * 
	 * @param Inventory
	 *            盘点实例
	 * @return 成功修改数量
	 * @throws Exception
	 *             修改失败异常
	 */
	public int updateInventory(Inventory inventory) throws Exception;

	/**
	 * 删除盘点
	 * 
	 * @param Inventory
	 *            盘点实例
	 * @return 成功删除数量
	 * @throws Exception
	 *             删除失败异常
	 */
	public int deleteInventory(Inventory inventory) throws Exception;

	/**
	 * 根据盘点编号查询盘点
	 * 
	 * @param id
	 *            盘点编号
	 * @return 盘点实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public Inventory selectInventoryById(int id) throws Exception;

	/**
	 * 根据map条件集合查询盘点
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的盘点集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<Inventory> selectInventoryByMap(HashMap<String, Object> map) throws Exception;

	public String selectMaxCode() throws Exception;

	public int selectLastIndexId() throws Exception;

	public int countInventoryByMap(HashMap<String, Object> map)throws Exception;
}
