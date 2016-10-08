package cn.hurry.data.mapper.operate;

import java.util.List;

import cn.hurry.po.operate.ManagerGroup;

/**
 * 管理组映射接口
 * 
 * 
 */
public interface ManagerGroupMapper {

	/**
	 * 根据管理组名称查询管理组
	 * 
	 * @param name
	 *            管理组名称
	 * @return 管理组
	 * @throws Exception
	 */
	public ManagerGroup selectManagerGroupByName(String name) throws Exception;

	/**
	 * 根据管理组编号查询管理组
	 * 
	 * @param id
	 *            管理组编号
	 * @return 管理组实例
	 * @throws Exception
	 */
	public ManagerGroup selectManagerGroupById(Integer id) throws Exception;

	/**
	 * 查询出所有的管理组
	 * 
	 * @return 管理组集合
	 * @throws Exception
	 */
	public List<ManagerGroup> selectAllManagerGroup() throws Exception;

	/**
	 * 增加一个管理组
	 * 
	 * @param managerGroup
	 *            管理组
	 * @return 成功条数
	 * @throws Exception
	 */
	public int insertManagerGroup(ManagerGroup managerGroup) throws Exception;

	/**
	 * 根据管理组编号更新管理组
	 * 
	 * @param managerGroup
	 *            管理组
	 * @return 成功条数
	 * @throws Exception
	 */
	public int updateManagerGroup(ManagerGroup managerGroup) throws Exception;

	/**
	 * 根据管理组编号删除管理组
	 * 
	 * @param id
	 *            管理组编号
	 * @return 成功条数
	 * @throws Exception
	 */
	public int deleteManagerGroup(Integer id) throws Exception;
}
