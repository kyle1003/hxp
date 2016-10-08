package cn.hurry.service.operate;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;


import cn.hurry.data.SessionFactory;
import cn.hurry.data.mapper.operate.ManagerGroupMapper;
import cn.hurry.data.mapper.operate.OperateGroupMapper;
import cn.hurry.manage.OperateManage;
import cn.hurry.po.operate.ManagerGroup;
import cn.hurry.po.operate.Operate;
import cn.hurry.po.operate.OperateGroup;
import cn.hurry.util.StringUtil;

/**
 * 管理组业务层
 * 
 * @author zh.sqy@qq.com
 * 
 */
@Service
public final class ManagerGroupService implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 根据管理组名称查询管理组
	 * 
	 * @param name
	 *            管理组名称
	 * @return 管理组实例
	 * @throws Exception
	 */
	public ManagerGroup selectManagerGroupByName(String name) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			ManagerGroupMapper mapper = session.getMapper(ManagerGroupMapper.class);
			return mapper.selectManagerGroupByName(name);
		} finally {
			session.close();
		}
	}

	/**
	 * 查询出所有的管理组
	 * 
	 * @return 管理组集合
	 * @throws Exception
	 */
	public List<ManagerGroup> selectAllManagerGroup() throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			ManagerGroupMapper mapper = session.getMapper(ManagerGroupMapper.class);
			return mapper.selectAllManagerGroup();
		} finally {
			session.close();
		}
	}

	/**
	 * 根据管理组编号查询管理组
	 * 
	 * @param id
	 *            管理组编号
	 * @return 管理组
	 * @throws Exception
	 */
	public ManagerGroup selectManagerGroupById(Integer id) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			ManagerGroupMapper mapper = session.getMapper(ManagerGroupMapper.class);
			return mapper.selectManagerGroupById(id);
		} finally {
			session.close();
		}
	}

	/**
	 * 根据管理组编号删除管理组
	 * 
	 * @param id
	 *            管理组编号
	 * @return 成功条数
	 * @throws Exception
	 */
	public void deleteManagerGroup(Integer id) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			ManagerGroupMapper mapper = session.getMapper(ManagerGroupMapper.class);
			mapper.deleteManagerGroup(id);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * 增加一个管理组
	 * 
	 * @param managerGroup
	 *            管理组
	 * @return 成功条数
	 */
	public void insertManagerGroup(ManagerGroup managerGroup) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			ManagerGroupMapper mapper = session.getMapper(ManagerGroupMapper.class);
			mapper.insertManagerGroup(managerGroup);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * 修改管理组信息(包括修改管理组的权限)
	 * 
	 * @param managerGroup
	 *            管理组
	 * @param oldOperateList
	 *            管理组的权限集合
	 * @param operateids
	 *            新权限编号数组
	 * @return 成功条数
	 * @throws Exception
	 */
	public int updateManagerGroup(ManagerGroup managerGroup, List<Operate> oldOperateList, String[] operateids) throws Exception {
		SqlSession session = SessionFactory.openSession();
		int result = 0;
		try {
			ManagerGroupMapper mapper = session.getMapper(ManagerGroupMapper.class);
			OperateGroupMapper operateGroupMapper = session.getMapper(OperateGroupMapper.class);
			result = mapper.updateManagerGroup(managerGroup);
			// 删除原管理组对应的权限
			if (null != oldOperateList && oldOperateList.size() > 0) {
				operateGroupMapper.deleteByManagerGroupId(managerGroup.getId());
			}
			// 添加修改 后的管理组对应的权限
			if (null != operateids && operateids.length > 0) {
				for (int i = 0; i < operateids.length; i++) {
					int oid = Integer.parseInt(operateids[i]);
					Operate dbOperate = OperateManage.getOperaMenuDataListById(oid);
					//
					if (dbOperate.getPid() != -1 && !StringUtil.containObjInArr(operateids, dbOperate.getPid() + "")) {
						OperateGroup operategroup = new OperateGroup();
						operategroup.setOperateId(dbOperate.getPid());
						operategroup.setManagerGroupId(managerGroup.getId());
						operateGroupMapper.insetOperateGroup(operategroup);
					}
					OperateGroup operategroup = new OperateGroup();
					// 为operategroup的非空字段赋值
					operategroup.setOperateId(oid);
					operategroup.setManagerGroupId(managerGroup.getId());
					operateGroupMapper.insetOperateGroup(operategroup);
				}
			}
			session.commit();
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
		}
		return result;
	}

}
