package cn.hurry.service.operate;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.hurry.data.SessionFactory;
import cn.hurry.data.mapper.operate.OperateMapper;
import cn.hurry.po.operate.Operate;

@Service
public class OperateService {
	
	/**
	 * 根据管理组编号查询出该管理组所拥有的权限
	 * 
	 * @param managerGroupId
	 *            管理组编号
	 * @return 权限集合
	 * @throws Exception
	 */
	public List<Operate> selectOperateByManagerGroupeId(Integer managerGroupId) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			OperateMapper mapper = session.getMapper(OperateMapper.class);
			return mapper.selectOperateByManagerGroupeId(managerGroupId);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public ArrayList<Operate> selectAllOperaMenuData() throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			OperateMapper mapper = session.getMapper(OperateMapper.class);
			return mapper.selectAllOperaMenuData();
		} finally {
		}
	}
}
