package cn.hurry.service.crm;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.hurry.data.SessionFactory;
import cn.hurry.data.mapper.crm.SupplierInfoMapper;
import cn.hurry.po.crm.SupplierInfo;

@Service
public class SupplierInfoService {

	public int countSupplierByMap(Map<String, Object> obj) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SupplierInfoMapper clientInfoMapper = session.getMapper(SupplierInfoMapper.class);
			return clientInfoMapper.countSupplierByMap(obj);
		} catch (Exception e) {
			return 0;
		} finally {
			session.close();
		}
	}

	public void deleteSupplierInfo(SupplierInfo info) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SupplierInfoMapper clientInfoMapper = session.getMapper(SupplierInfoMapper.class);
			clientInfoMapper.deleteSupplierInfo(info);
			session.commit();
		} finally {
			session.close();
		}
	}

	public void insertSupplierInfo(SupplierInfo info) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SupplierInfoMapper clientInfoMapper = session.getMapper(SupplierInfoMapper.class);
			clientInfoMapper.insertSupplierInfo(info);
			session.commit();
		} finally {
			session.close();
		}
	}

	public SupplierInfo selectSupplierById(int id) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SupplierInfoMapper clientInfoMapper = session.getMapper(SupplierInfoMapper.class);
			return clientInfoMapper.selectSupplierById(id);
		} finally {
			session.close();
		}
	}

	public List<SupplierInfo> selectSupplierByMap(Map<String, Object> obj) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SupplierInfoMapper clientInfoMapper = session.getMapper(SupplierInfoMapper.class);
			return clientInfoMapper.selectSupplierByMap(obj);
		} finally {
			session.close();
		}
	}

	public void updateSupplierInfo(SupplierInfo info) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SupplierInfoMapper clientInfoMapper = session.getMapper(SupplierInfoMapper.class);
			clientInfoMapper.updateSupplierInfo(info);
			session.commit();
		} finally {
			session.close();
		}
	}

}
