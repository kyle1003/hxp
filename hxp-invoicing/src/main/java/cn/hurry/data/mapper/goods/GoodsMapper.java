package cn.hurry.data.mapper.goods;

import java.util.List;
import java.util.Map;

import cn.hurry.po.goods.Goods;

/**
 * 商品映射接口
 * 
 * @author ZhouHao
 * 
 */
public interface GoodsMapper {
	/**
	 * 添加商品
	 * 
	 * @param Goods
	 *            商品实例
	 * @return 成功添加数量
	 * @throws Exception
	 *             添加失败异常
	 */
	public int insertGoods(Goods goods) throws Exception;

	/**
	 * 修改商品
	 * 
	 * @param Goods
	 *            商品实例
	 * @return 成功修改数量
	 * @throws Exception
	 *             修改失败异常
	 */
	public int updateGoods(Goods goods) throws Exception;
	
	/**
	 * 修改商品库存
	 * 
	 * @param Goods
	 *            商品实例
	 * @return 成功修改数量
	 * @throws Exception
	 *             修改失败异常
	 */
	public int updateGoodsNumber(Goods goods) throws Exception;
	
	/**
	 * 删除商品
	 * 
	 * @param Goods
	 *            商品实例
	 * @return 成功删除数量
	 * @throws Exception
	 *             删除失败异常
	 */
	public int deleteGoods(Goods goods) throws Exception;

	/**
	 * 根据商品编号查询商品
	 * 
	 * @param id
	 *            商品编号
	 * @return 商品实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public Goods selectGoodsById(int id) throws Exception;

	/**
	 * 根据map条件集合查询商品
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的商品集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<Goods> selectGoodsByMap(Map<String, Object> map)
			throws Exception;
}
