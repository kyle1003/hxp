package cn.hurry.data.mapper.loss;

import java.util.List;
import java.util.Map;

import cn.hurry.po.loss.Loss;

/**
 * 损耗映射接口
 * 
 * @author ZhouHao
 * 
 */
public interface LossMapper {
	/**
	 * 添加损耗
	 * 
	 * @param Loss
	 *            损耗实例
	 * @return 成功添加数量
	 * @throws Exception
	 *             添加失败异常
	 */
	public int insertLoss(Loss loss) throws Exception;

	/**
	 * 修改损耗
	 * 
	 * @param Loss
	 *            损耗实例
	 * @return 成功修改数量
	 * @throws Exception
	 *             修改失败异常
	 */
	public int updateLoss(Loss loss) throws Exception;

	/**
	 * 删除损耗
	 * 
	 * @param Loss
	 *            损耗实例
	 * @return 成功删除数量
	 * @throws Exception
	 *             删除失败异常
	 */
	public int deleteLoss(Loss loss) throws Exception;

	/**
	 * 根据损耗编号查询损耗
	 * 
	 * @param id
	 *            损耗编号
	 * @return 损耗实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public Loss selectLossById(int id) throws Exception;

	/**
	 * 根据map条件集合查询损耗
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的损耗集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<Loss> selectLossByMap(Map<String, Object> map) throws Exception;

	public int countLossByMap(Map<String, Object> map) throws Exception;;
}
