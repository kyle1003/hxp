package cn.hurry.data.mapper.operate;

import java.util.List;

import cn.hurry.po.operate.OperateGroup;

/**
 * 权限组映射接口
 * 
 * @author zh.sqy@qq.com
 * 
 */
public interface OperateGroupMapper {
	/**
	 * 根据权限组编号查询权限组
	 * 
	 * @param id
	 *            权限组编号
	 * @return 权限组
	 * @throws Exception
	 */
	public OperateGroup selectOperateGroupById(Integer id) throws Exception;

	/**
	 * 根据权限编号查询权限组
	 * 
	 * @param operateId
	 *            权限编号
	 * @return 权限组集合
	 * @throws Exception
	 */
	public List<OperateGroup> selectOperateGroupByOperateId(Integer operateId) throws Exception;

	/**
	 * 根据管理组编号查询权限
	 * 
	 * @param managerGroupId
	 *            管理组编号
	 * @return 权限组集合
	 * @throws Exception
	 */
	public List<OperateGroup> selectOperateGroupByManagerGroupId(Integer managerGroupId) throws Exception;

	/**
	 * 查询所有权限组
	 * 
	 * @return 权限组集合
	 * @throws Exception
	 */
	public List<OperateGroup> selectAllOperateGroup() throws Exception;

	/**
	 * 增加一个权限组
	 * 
	 * @param operateGroup
	 *            权限组
	 * @return 成功条数
	 * @throws Exception
	 */
	public int insetOperateGroup(OperateGroup operateGroup) throws Exception;

	/**
	 * 根据权限组编号更新权限
	 * 
	 * @param operateGroup
	 *            权限组
	 * @return 成功条数
	 * @throws Exception
	 */
	public int updateOperateGroup(OperateGroup operateGroup) throws Exception;

	/**
	 * 根据权限组编号删除权限组
	 * 
	 * @param id
	 *            权限组编号
	 * @return 成功条数
	 * @throws Exception
	 */
	public int deleteOperateGroup(Integer id) throws Exception;

	/**
	 * 根据管理组编号删除分组权限
	 * 
	 * @param groupId
	 *            管理组编号
	 * @return 成功条数
	 * @throws Exception
	 */
	public int deleteByManagerGroupId(int managerGroupId) throws Exception;
}
