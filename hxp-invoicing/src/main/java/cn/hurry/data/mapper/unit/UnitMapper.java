package cn.hurry.data.mapper.unit;

import java.util.HashMap;
import java.util.List;

import cn.hurry.po.unit.Unit;

/**
 * 单位映射接口
 * 
 * @author ZhouHao
 * 
 */
public interface UnitMapper {
	/**
	 * 添加单位
	 * 
	 * @param unit
	 *            单位实例
	 * @return 成功添加数量
	 * @throws Exception
	 *             添加失败异常
	 */
	public int insertUnit(Unit unit) throws Exception;

	/**
	 * 修改单位
	 * 
	 * @param unit
	 *            单位实例
	 * @return 成功修改数量
	 * @throws Exception
	 *             修改失败异常
	 */
	public int updateUnit(Unit unit) throws Exception;

	/**
	 * 删除单位
	 * 
	 * @param unit
	 *            单位实例
	 * @return 成功删除数量
	 * @throws Exception
	 *             删除失败异常
	 */
	public int deleteUnit(Unit unit) throws Exception;

	/**
	 * 根据单位编号查询单位
	 * 
	 * @param id
	 *            单位编号
	 * @return 单位实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public Unit selectUnitById(int id) throws Exception;

	/**
	 * 根据map条件集合查询单位
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的单位集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<Unit> selectUnitByMap(HashMap<String, Object> map)
			throws Exception;
}
