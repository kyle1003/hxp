package cn.hurry.data.mapper.goods;

import java.util.HashMap;
import java.util.List;

import cn.hurry.po.goods.GoodsType;

/**
 * 商品类别映射接口
 * 
 * @author ZhouHao
 * 
 */
public interface GoodsTypeMapper {
	/**
	 * 添加商品类别
	 * 
	 * @param GoodsType
	 *            商品类别实例
	 * @return 成功添加数量
	 * @throws Exception
	 *             添加失败异常
	 */
	public int insertGoodsType(GoodsType goodsType) throws Exception;

	/**
	 * 修改商品类别
	 * 
	 * @param GoodsType
	 *            商品类别实例
	 * @return 成功修改数量
	 * @throws Exception
	 *             修改失败异常
	 */
	public int updateGoodsType(GoodsType goodsType) throws Exception;

	/**
	 * 删除商品类别
	 * 
	 * @param GoodsType
	 *            商品类别实例
	 * @return 成功删除数量
	 * @throws Exception
	 *             删除失败异常
	 */
	public int deleteGoodsType(GoodsType goodsType) throws Exception;

	/**
	 * 根据商品类别编号查询商品类别
	 * 
	 * @param id
	 *            商品类别编号
	 * @return 商品类别实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public GoodsType selectGoodsTypeById(int id) throws Exception;

	/**
	 * 根据map条件集合查询商品类别
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的商品类别集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<GoodsType> selectGoodsTypeByMap(HashMap<String, Object> map)
			throws Exception;
}
