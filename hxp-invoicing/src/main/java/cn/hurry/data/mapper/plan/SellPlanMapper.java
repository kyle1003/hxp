package cn.hurry.data.mapper.plan;

/**
 * 销售方案映射接口
 * 
 * @author ZhouHao
 * 
 */
public interface SellPlanMapper {

	/**
	 * 修改销售方案
	 * 
	 * @param type
	 *            方案类型
	 * @return 成功修改数量
	 * @throws Exception
	 *             修改失败异常
	 */
	public int updateSellPlan(byte type) throws Exception;

	/**
	 * 查询方案
	 * 
	 * @return 销售方案实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public byte selectSellPlan() throws Exception;

}
