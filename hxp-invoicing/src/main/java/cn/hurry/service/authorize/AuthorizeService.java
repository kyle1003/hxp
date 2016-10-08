package cn.hurry.service.authorize;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.hurry.data.SessionFactory;
import cn.hurry.data.mapper.authorize.AuthorizeMapper;
import cn.hurry.po.authorize.Authorize;

/**
 * 授权服务类
 * 
 * @author zh.sqy@qq.com
 * 
 */
@Service
public class AuthorizeService {

	
	/**
	 * 获取授权
	 * 
	 * @return 授权
	 * @throws Exception
	 */
	public Authorize getAuthorize() throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			AuthorizeMapper authorizeMapper = session.getMapper(AuthorizeMapper.class);
			return authorizeMapper.selectAuthorize();
		} finally {
			session.close();
		}
	}

	/**
	 * 新增授权
	 * 
	 * @param authorize
	 *            授权
	 * @throws Exception
	 */
	public void addAuthorize(Authorize authorize) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			AuthorizeMapper authorizeMapper = session.getMapper(AuthorizeMapper.class);
			authorizeMapper.insertAuthorize(authorize);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * 更新授权
	 * 
	 * @param authorize
	 *            授权
	 * @throws Exception
	 */
	public void updateAuthorize(Authorize authorize) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			AuthorizeMapper authorizeMapper = session.getMapper(AuthorizeMapper.class);
			authorizeMapper.updateAuthorize(authorize);
			session.commit();
		} finally {
			session.close();
		}
	}

	
	
}
