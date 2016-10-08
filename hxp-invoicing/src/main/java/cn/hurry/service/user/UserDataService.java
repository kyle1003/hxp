package cn.hurry.service.user;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.hurry.data.SessionFactory;
import cn.hurry.data.mapper.user.UserDataMapper;
import cn.hurry.po.user.UserData;

/**
 * 用户数据服务类
 * 
 * @author zh.sqy@qq.com
 * 
 */
@Service
public class UserDataService {

	public void addUserData(UserData userData) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			UserDataMapper mapper = session.getMapper(UserDataMapper.class);
			mapper.insertUserData(userData);
			session.commit();
		} finally {
			session.close();
		}
	}

	public void updateUserData(UserData userData) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			UserDataMapper mapper = session.getMapper(UserDataMapper.class);
			mapper.updateUserDataById(userData);
			session.commit();
		} finally {
			session.close();
		}
	}

	public UserData getUserData(int id) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			UserDataMapper mapper = session.getMapper(UserDataMapper.class);
			return mapper.selectUserDataById(id);
		} finally {
			session.close();
		}
	}

}
