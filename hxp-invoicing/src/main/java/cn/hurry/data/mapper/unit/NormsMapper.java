package cn.hurry.data.mapper.unit;

import java.util.HashMap;
import java.util.List;

import cn.hurry.po.unit.Norms;

/**
 * 规格映射接口
 * 
 * @author ZhouHao
 * 
 */
public interface NormsMapper {
	/**
	 * 添加规格
	 * 
	 * @param Norms
	 *            规格实例
	 * @return 成功添加数量
	 * @throws Exception
	 *             添加失败异常
	 */
	public int insertNorms(Norms norms) throws Exception;

	/**
	 * 修改规格
	 * 
	 * @param Norms
	 *            规格实例
	 * @return 成功修改数量
	 * @throws Exception
	 *             修改失败异常
	 */
	public int updateNorms(Norms norms) throws Exception;

	/**
	 * 删除规格
	 * 
	 * @param Norms
	 *            规格实例
	 * @return 成功删除数量
	 * @throws Exception
	 *             删除失败异常
	 */
	public int deleteNorms(Norms norms) throws Exception;

	/**
	 * 根据规格编号查询规格
	 * 
	 * @param id
	 *            规格编号
	 * @return 规格实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public Norms selectNormsById(int id) throws Exception;

	/**
	 * 根据map条件集合查询规格
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的规格集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<Norms> selectNormsByMap(HashMap<String, Object> map)
			throws Exception;
}
