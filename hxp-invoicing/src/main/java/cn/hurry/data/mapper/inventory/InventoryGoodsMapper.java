package cn.hurry.data.mapper.inventory;

import java.util.HashMap;
import java.util.List;

import cn.hurry.po.inventory.InventoryGoods;

/**
 * 盘点商品映射接口
 * 
 * @author ZhouHao
 * 
 */
public interface InventoryGoodsMapper {
	/**
	 * 添加盘点商品
	 * 
	 * @param InventoryGoods
	 *            盘点商品实例
	 * @return 成功添加数量
	 * @throws Exception
	 *             添加失败异常
	 */
	public int insertInventoryGoods(InventoryGoods inventoryGoods) throws Exception;

	/**
	 * 修改盘点商品
	 * 
	 * @param InventoryGoods
	 *            盘点商品实例
	 * @return 成功修改数量
	 * @throws Exception
	 *             修改失败异常
	 */
	public int updateInventoryGoods(InventoryGoods inventoryGoods) throws Exception;

	/**
	 * 修改盘点商品库存
	 * 
	 * @param InventoryGoods
	 *            盘点商品实例
	 * @return 成功修改数量
	 * @throws Exception
	 *             修改失败异常
	 */
	public int updateInventoryGoodsNumber(InventoryGoods inventoryGoods) throws Exception;

	/**
	 * 删除盘点商品
	 * 
	 * @param InventoryGoods
	 *            盘点商品实例
	 * @return 成功删除数量
	 * @throws Exception
	 *             删除失败异常
	 */
	public int deleteInventoryGoods(InventoryGoods inventoryGoods) throws Exception;

	/**
	 * 根据盘点商品编号查询盘点商品
	 * 
	 * @param id
	 *            盘点商品编号
	 * @return 盘点商品实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public InventoryGoods selectInventoryGoodsById(int id) throws Exception;

	public List<InventoryGoods>  selectInventoryGoodsByInventoryId(int id) throws Exception;

	/**
	 * 根据map条件集合查询盘点商品
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的盘点商品集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<InventoryGoods> selectInventoryGoodsByMap(HashMap<String, Object> map) throws Exception;

}
