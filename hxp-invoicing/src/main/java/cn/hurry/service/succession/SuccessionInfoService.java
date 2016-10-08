package cn.hurry.service.succession;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.hurry.data.SessionFactory;
import cn.hurry.data.mapper.succession.SuccessionInfoMapper;
import cn.hurry.manage.succession.SuccessionInfoManage;
import cn.hurry.po.succession.SuccessionInfo;

@Service
public class SuccessionInfoService {

	public SuccessionInfo insertSuccessionInfo(SuccessionInfo successionInfo) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SuccessionInfoMapper successionInfoMapper = session.getMapper(SuccessionInfoMapper.class);
			successionInfoMapper.insertSuccessionInfo(successionInfo);
			int id = successionInfoMapper.selectLastInsertId();
			successionInfo = successionInfoMapper.selectSuccessionInfoById(id);
			session.commit();
			SuccessionInfoManage.put(successionInfo);
			return successionInfo;
		} finally {
			session.close();
		}
	}

	public SuccessionInfo selectSuccessionInfoById(int id) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SuccessionInfoMapper successionInfoMapper = session.getMapper(SuccessionInfoMapper.class);
			return successionInfoMapper.selectSuccessionInfoById(id);
		} finally {
			session.clearCache();
			session.close();
		}
	}

	public List<SuccessionInfo> selectSuccessionInfoByMap(Map<String, Object> map) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SuccessionInfoMapper successionInfoMapper = session.getMapper(SuccessionInfoMapper.class);
			return successionInfoMapper.selectSuccessionInfoByMap(map);
		} finally {
			session.close();
		}
	}
	
	public int countSuccessionInfoByMap(Map<String, Object> map) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SuccessionInfoMapper successionInfoMapper = session.getMapper(SuccessionInfoMapper.class);
			return successionInfoMapper.countSuccessionInfoByMap(map);
		}catch (Exception e) {
			return 0;
		} finally {
			session.close();
		}
	}

	public void updateSuccessionInfo(SuccessionInfo successionInfo) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SuccessionInfoMapper successionInfoMapper = session.getMapper(SuccessionInfoMapper.class);
			successionInfoMapper.updateSuccessionInfo(successionInfo);
			successionInfo = successionInfoMapper.selectSuccessionInfoById(successionInfo.getId());
			session.commit();
			SuccessionInfoManage.put(successionInfo);
		} finally {
			session.close();
		}
	}

}
