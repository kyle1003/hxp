package cn.hurry.service.store;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.hurry.data.SessionFactory;
import cn.hurry.data.mapper.store.StoreMapper;
import cn.hurry.data.mapper.user.UserMapper;
import cn.hurry.manage.store.StoreManage;
import cn.hurry.po.store.Store;

@Service
public class StoreService {

	/**
	 * 删除仓库
	 * 
	 * @param Store
	 *            仓库实例
	 * @throws Exception
	 *             删除失败异常
	 */
	public void deleteStore(Store store) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			StoreMapper StoreMapper = session.getMapper(StoreMapper.class);
			StoreMapper.deleteStore(store);
			session.commit();
			// 删除内存中的数据
			StoreManage.removeStore(store);
		} finally {
			session.close();
		}
	}

	/**
	 * 添加仓库
	 * 
	 * @param Store
	 *            仓库实例
	 * @throws Exception
	 *             添加失败异常
	 */
	public void insertStore(Store store) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			StoreMapper StoreMapper = session.getMapper(StoreMapper.class);
			UserMapper userMapper = session.getMapper(UserMapper.class);
			Store dbStore = StoreManage.getStoreByName(store.getName());
			if (dbStore != null) {
				throw new Exception("仓库名称已存在!");
			}
			StoreMapper.insertStore(store);
			store.setId(userMapper.selectLastInsertId());
			store.setUser(userMapper.selectUserById(store.getUserId()));
			session.commit();
			StoreManage.addStore(store);
		} finally {
			session.close();
		}
	}

	/**
	 * 根据仓库编号查询仓库
	 * 
	 * @param id
	 *            仓库编号
	 * @return 仓库实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public Store selectStoreById(int id) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			StoreMapper StoreMapper = session.getMapper(StoreMapper.class);
			return StoreMapper.selectStoreById(id);
		} finally {
			session.close();
		}
	}

	/**
	 * 根据map条件集合查询仓库
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的仓库集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<Store> selectStoreByMap(HashMap<String, Object> map) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			StoreMapper StoreMapper = session.getMapper(StoreMapper.class);
			return StoreMapper.selectStoreByMap(map);
		} finally {
			session.close();
		}
	}

	/**
	 * 修改仓库
	 * 
	 * @param Store
	 *            仓库实例
	 * @throws Exception
	 *             修改失败异常
	 */
	public void updateStore(Store store) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			StoreMapper StoreMapper = session.getMapper(StoreMapper.class);
			UserMapper userMapper = session.getMapper(UserMapper.class);
			Store dbStore = StoreManage.getStoreByName(store.getName());
			if (dbStore != null && dbStore.getId() != store.getId()) {
				throw new Exception("仓库名称已存在!");
			}
			StoreMapper.updateStore(store);
			store.setUser(userMapper.selectUserById(store.getUserId()));
			session.commit();
			StoreManage.addStore(store);
		} finally {
			session.close();
		}
	}

}
