package cn.hurry.data.mapper.order.sell;

import java.util.List;
import java.util.Map;

import cn.hurry.po.order.sell.SellInfo;

/**
 * 销售单详情映射接口
 * 
 * @author ZhouHao
 * 
 */
public interface SellInfoMapper {
	/**
	 * 添加销售单详情
	 * 
	 * @param SellOrder
	 *            销售单详情实例
	 * @return 成功添加数量
	 * @throws Exception
	 *             添加失败异常
	 */
	public int insertSellInfo(SellInfo order) throws Exception;

	/**
	 * 根据销售单详情编号查询销售单详情
	 * 
	 * @param id
	 *            销售单详情编号
	 * @return 销售单详情实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public SellInfo selectSellInfoById(int id) throws Exception;

	/**
	 * 根据map条件集合查询销售单详情
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的销售单详情集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<SellInfo> selectSellInfoByMap(Map<String, Object> map) throws Exception;

	public int countSellInfoByMap(Map<String, Object> map) throws Exception;

}
