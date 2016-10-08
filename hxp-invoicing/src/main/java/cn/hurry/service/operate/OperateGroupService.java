package cn.hurry.service.operate;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.hurry.data.SessionFactory;
import cn.hurry.data.mapper.operate.OperateGroupMapper;
import cn.hurry.po.operate.OperateGroup;

/**
 * 权限组业务类
 * 
 * @author zh.sqy@qq.com
 * 
 */
@Service
public final class OperateGroupService implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 根据管理组编号查询权限
	 * 
	 * @param managerGroupId
	 *            管理组编号
	 * @return 权限组集合
	 * @throws Exception
	 */
	public List<OperateGroup> selectOperateGroupByManagerGroupId(Integer managerGroupId) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			OperateGroupMapper mapper = session.getMapper(OperateGroupMapper.class);
			return mapper.selectOperateGroupByManagerGroupId(managerGroupId);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 根据权限编号查询权限组
	 * 
	 * @param operateId
	 *            权限编号
	 * @return 权限组集合
	 * @throws Exception
	 */
	public List<OperateGroup> selectOperateGroupByOperateId(Integer operateId) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			OperateGroupMapper mapper = session.getMapper(OperateGroupMapper.class);
			return mapper.selectOperateGroupByOperateId(operateId);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 查询所有权限组
	 * 
	 * @return 权限组集合
	 * @throws Exception
	 */
	public List<OperateGroup> selectAllOperateGroup() throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			OperateGroupMapper mapper = session.getMapper(OperateGroupMapper.class);
			return mapper.selectAllOperateGroup();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 根据权限组编号查询权限组
	 * 
	 * @param id
	 *            权限组编号
	 * @return 权限组
	 * @throws Exception
	 */
	public OperateGroup selectOperateGroupById(Integer id) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			OperateGroupMapper mapper = session.getMapper(OperateGroupMapper.class);
			return mapper.selectOperateGroupById(id);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 根据管理组编号删除分组权限
	 * 
	 * @param managerGroupId
	 *            管理组编号
	 * @throws Exception
	 */
	public void deleteByManagerGroupId(int managerGroupId) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			OperateGroupMapper mapper = session.getMapper(OperateGroupMapper.class);
			mapper.deleteByManagerGroupId(managerGroupId);
			session.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 根据权限编号删除权限组
	 * 
	 * @param id
	 *            权限组编号
	 * @throws Exception
	 */
	public void deleteOperateGroup(Integer id) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			OperateGroupMapper mapper = session.getMapper(OperateGroupMapper.class);
			mapper.deleteOperateGroup(id);
			session.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 增加一个权限组
	 * 
	 * @param operateGroup
	 *            权限组
	 * @throws Exception
	 */
	public void insetOperateGroup(OperateGroup operateGroup) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			OperateGroupMapper mapper = session.getMapper(OperateGroupMapper.class);
			mapper.insetOperateGroup(operateGroup);
			session.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 根据权限组编号更新权限
	 * 
	 * @param operateGroup
	 *            权限组
	 * @throws Exception
	 */
	public void updateOperateGroup(OperateGroup operateGroup) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			OperateGroupMapper mapper = session.getMapper(OperateGroupMapper.class);
			mapper.updateOperateGroup(operateGroup);
			session.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
