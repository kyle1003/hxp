package cn.hurry.data.mapper.user;

import cn.hurry.po.user.UserData;

/**
 * 用户数据映射接口
 * 
 * @author zh.sqy@qq.com
 * 
 */
public interface UserDataMapper {

	/**
	 * 新增用户数据
	 * 
	 * @param userData
	 *            用户数据
	 * @return 成功新增条数
	 * @throws Exception
	 */
	public int insertUserData(UserData userData) throws Exception;

	/**
	 * 根据用户编号更新用户数据
	 * 
	 * @param userData
	 *            用户数据
	 * @return 成功更新条数
	 * @throws Exception
	 */
	public int updateUserDataById(UserData userData) throws Exception;

	/**
	 * 根据用户编号查询用户数据
	 * 
	 * @param id
	 *            用户编号
	 * @return 用户数据
	 * @throws Exception
	 */
	public UserData selectUserDataById(int id) throws Exception;

	/**
	 * 根据用户编号删除用户数据
	 * 
	 * @param id
	 *            用户编号
	 * @return 成功删除条数
	 * @throws Exception
	 */
	public int deleteUserDataById(int id) throws Exception;

}