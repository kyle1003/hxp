package cn.hurry.service.crm;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.hurry.data.SessionFactory;
import cn.hurry.data.mapper.crm.ClientInfoMapper;
import cn.hurry.po.crm.ClientInfo;

@Service
public class ClientInfoService {

	public int countClientByMap(Map<String, Object> obj) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			ClientInfoMapper clientInfoMapper = session.getMapper(ClientInfoMapper.class);
			return clientInfoMapper.countClientByMap(obj);
		} catch (Exception e) {
			return 0;
		} finally {
			session.close();
		}
	}

	public void deleteClientInfo(ClientInfo info) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			ClientInfoMapper clientInfoMapper = session.getMapper(ClientInfoMapper.class);
			clientInfoMapper.deleteClientInfo(info);
			session.commit();
		} finally {
			session.close();
		}
	}

	public void insertClientInfo(ClientInfo info) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			ClientInfoMapper clientInfoMapper = session.getMapper(ClientInfoMapper.class);
			clientInfoMapper.insertClientInfo(info);
			session.commit();
		} finally {
			session.close();
		}
	}

	public ClientInfo selectClientById(int id) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			ClientInfoMapper clientInfoMapper = session.getMapper(ClientInfoMapper.class);
			return clientInfoMapper.selectClientById(id);
		} finally {
			session.close();
		}
	}

	public List<ClientInfo> selectClientByMap(Map<String, Object> obj) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			ClientInfoMapper clientInfoMapper = session.getMapper(ClientInfoMapper.class);
			return clientInfoMapper.selectClientByMap(obj);
		} finally {
			session.close();
		}
	}

	public void updateClientInfo(ClientInfo info) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			ClientInfoMapper clientInfoMapper = session.getMapper(ClientInfoMapper.class);
			clientInfoMapper.updateClientInfo(info);
			session.commit();
		} finally {
			session.close();
		}
	}
	

}
