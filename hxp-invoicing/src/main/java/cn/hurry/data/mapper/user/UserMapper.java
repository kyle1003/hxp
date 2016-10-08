package cn.hurry.data.mapper.user;

import java.util.ArrayList;
import java.util.HashMap;

import cn.hurry.po.user.User;

/**
 * 用户映射接口
 * 
 * @author zh.sqy@qq.com
 * 
 */
public interface UserMapper {
	/**
	 * 获取最后一条插入对象
	 * 
	 * @return 最后一条插入对象
	 * @throws Exception
	 */
	public int selectLastInsertId() throws Exception;

	/**
	 * 根据用户编号查询用户
	 * 
	 * @param id
	 *            用户编号
	 * @return 用户
	 */
	public User selectUserById(Integer id) throws Exception;

	/**
	 * 根据用户名查询用户
	 * 
	 * @param userName
	 *            用户名
	 * @return 用户
	 */
	public User selectUserByUsername(String userName) throws Exception;

	/**
	 * 根据用户名和密码查询用户
	 * 
	 * @param user
	 *            用户
	 * @return 用户
	 */
	public User selectUserByUsernameAndPass(User user) throws Exception;

	/**
	 * 查询所有用户
	 * 
	 * @return 用户集合
	 */
	public ArrayList<User> selectAllUser() throws Exception;

	/**
	 * 查询有效的用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList<User> selectValidUser() throws Exception;

	/**
	 * 根据条件查询用户（可分页）
	 * 
	 * @param parameterMap
	 *            条件参数
	 * @return 用户列表
	 * @throws Exception
	 */
	public ArrayList<User> selectUser(HashMap<String, Object> parameterMap) throws Exception;

	/**
	 * 根据条件查询用户数量
	 * 
	 * @param parameterMap
	 *            条件参数
	 * @return 用户数量
	 * @throws Exception
	 */
	public int selectUserCount(HashMap<String, Object> parameterMap) throws Exception;

	/**
	 * 添加一个用户
	 * 
	 * @param user
	 *            用户
	 * @return 成功条数
	 */
	public int insertUser(User user) throws Exception;

	/**
	 * 更新用户
	 * 
	 * @param user
	 *            用户
	 * @return 成功条数
	 */
	public int updateUserById(User user) throws Exception;

	/**
	 * 根据编号删除用户
	 * 
	 * @param id
	 *            用户编号
	 * @return 成功条数
	 */
	public int deleteUserById(Integer id) throws Exception;

	/**
	 * 查询所有有未验收的采购单的人员列表
	 * 
	 * @return 人员列表
	 * @throws Exception
	 */
	public ArrayList<User> selectPurchaseImpUsers() throws Exception;

}