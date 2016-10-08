package cn.hurry.service.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import cn.hurry.data.SessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import cn.hurry.data.mapper.user.UserDataMapper;
import cn.hurry.data.mapper.user.UserMapper;
import cn.hurry.po.user.User;
import cn.hurry.po.user.UserData;
import cn.hurry.util.MD5;

/**
 * 用户类业务层
 * 
 * @author zh.sqy@qq.com
 */
@Service
public final class UserService implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 根据用户名和密码获取用户
	 * 
	 * @param user
	 *            用户
	 * @return 用户
	 * @throws Exception
	 */
	public User getUserByUsernameAndPass(User user) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			return mapper.selectUserByUsernameAndPass(user);
		} finally {
			session.close();
		}
	}

	/**
	 * 根据用户编号获取用户
	 * 
	 * @param id
	 *            用户编号
	 * @return 用户
	 * @throws Exception
	 */
	public User getUserById(Integer id) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			return mapper.selectUserById(id);
		} finally {
			session.close();
		}
	}

	/**
	 * 根据用户名获取用户
	 * 
	 * @param username
	 *            用户名
	 * @return 用户
	 * @throws Exception
	 */
	public User getUserByUsername(String username) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			return mapper.selectUserByUsername(username);
		} finally {
			session.close();
		}
	}

	/**
	 * 获取所有用户
	 * 
	 * @return 用户列表
	 * @throws Exception
	 */
	public ArrayList<User> getAllUser() throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			return mapper.selectAllUser();
		} finally {
			session.close();
		}
	}

	/**
	 * 查询有效的用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList<User> selectValidUser() throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			return mapper.selectValidUser();
		} finally {
			session.close();
		}
	}

	/**
	 * 根据条件获取用户列表
	 * 
	 * @param parameterMap
	 *            条件参数
	 * @return 用户列表
	 * @throws Exception
	 */
	public ArrayList<User> getUser(HashMap<String, Object> parameterMap) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			return mapper.selectUser(parameterMap);
		} finally {
			session.close();
		}
	}

	/**
	 * 根据条件获取用户数量
	 * 
	 * @param parameterMap
	 *            条件参数
	 * @return 用户数量
	 * @throws Exception
	 */
	public int getUserCount(HashMap<String, Object> parameterMap) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			return mapper.selectUserCount(parameterMap);
		} finally {
			session.close();
		}
	}

	/**
	 * 根据编号删除用户
	 * 
	 * @param userId
	 *            用户编号
	 * @throws Exception
	 */
	public void deleteUser(Integer userId) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			UserDataMapper userDataMapper = session.getMapper(UserDataMapper.class);
			mapper.deleteUserById(userId);
			userDataMapper.deleteUserDataById(userId);
			session.commit();
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * 添加一个用户
	 * 
	 * @param user
	 *            用户实例
	 * -
	 *         userData   用户详情
	 * @throws Exception
	 */
	public void insertUser(User user) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			UserDataMapper datamapper = session.getMapper(UserDataMapper.class);
			User dbUser = mapper.selectUserByUsername(user.getUsername());
			if (dbUser!=null) {
				throw new Exception("用户名已存在");
			}
			user.setCreateDatetime(new Date());
			user.setPassword(MD5.encode(user.getPassword()));
			mapper.insertUser(user);
			int userId = mapper.selectLastInsertId();
			// 如果要添加一个详情
			if (null != user.getUserData()) {
				UserData userData =user.getUserData();
				userData.setId(userId);
				Date date = new Date();
				userData.setCreateDatetime(date);
				userData.setLastChangeDatetime(date);
				datamapper.insertUserData(userData);
			}
			session.commit();
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * 更新用户
	 * 
	 * @param user
	 *            用户
	 * @throws Exception
	 */
	public void updateUser(User user) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			UserDataMapper datamapper = session.getMapper(UserDataMapper.class);
			User dbUser = mapper.selectUserByUsername(user.getUsername());
			if (dbUser!=null&&dbUser.getId() != user.getId()) {
				throw new Exception("用户名已存在");
			}
			mapper.updateUserById(user);
			// 对详情进行了修改
			if (null != user.getUserData()) {
				UserData userData =user.getUserData();
				Date date = new Date();
				if (null != datamapper.selectUserDataById(user.getId())) {
					// 数据据中有则修改
					userData.setLastChangeDatetime(date);
					datamapper.updateUserDataById(userData);
				} else {
					// 没有则添加
					userData.setCreateDatetime(date);
					userData.setLastChangeDatetime(date);
					datamapper.insertUserData(userData);
				}
			}
			session.commit();
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * 查询所有有未验收的采购单的人员列表
	 * 
	 * @return 人员列表
	 * @throws Exception
	 */
	public ArrayList<User> getPurchaseImpUsers() throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			return mapper.selectPurchaseImpUsers();
		} finally {
			session.close();
		}
	}

	/**
	 * 获取最后一条添加的数据ID
	 * 
	 * @return 最后一条添加的数据ID
	 * @throws Exception
	 */
	public int selectLastInsertId() throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			return mapper.selectLastInsertId();
		} finally {
			session.close();
		}

	}

}
