package cn.hurry.manage.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hurry.po.store.Store;
import cn.hurry.service.store.StoreService;

/**
 * 仓库管理类
 * 
 * @author ZhouHao
 * 
 */
public class StoreManage {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(StoreManage.class);

	/**
	 * 仓库库
	 */
	private static final HashMap<Integer, Store> STORE_MAP = new HashMap<Integer, Store>();

	static {
		init();
	}

	/**
	 * 加载数据
	 */
	private static void init() {
		try {
			List<Store> stores = new StoreService().selectStoreByMap(null);
			for (Store store : stores) {
				STORE_MAP.put(store.getId(), store);
			}
		} catch (Exception e) {
			logger.error("加载仓库失败", e);
		}
	}

	/**
	 * 获取所有仓库
	 * 
	 * @return 所有仓库
	 */
	public static List<Store> getAllStore() {
		synchronized (STORE_MAP) {
			return new ArrayList<Store>(STORE_MAP.values());
		}
	}

	/**
	 * 根据编号获取仓库
	 * 
	 * @param id
	 *            仓库编号
	 * @return 仓库
	 */
	public static Store getStoreById(int id) {
		for (Store store : getAllStore()) {
			if (store.getId() == id) {
				return store;
			}
		}
		return null;
	}

	/**
	 * 根据名称获取仓库
	 * 
	 * @param name
	 *            仓库名称
	 * @return 仓库
	 */
	public static Store getStoreByName(String name) {
		for (Store store : getAllStore()) {
			if (store.getName().equals(name)) {
				return store;
			}
		}
		return null;
	}

	/**
	 * 添加仓库
	 * 
	 * @param store
	 *            仓库
	 */
	public static void addStore(Store store) {
		synchronized (STORE_MAP) {
			STORE_MAP.put(store.getId(), store);
		}
	}

	/**
	 * 删除仓库
	 * 
	 * @param store
	 *            仓库
	 */
	public static void removeStore(Store store) {
		synchronized (STORE_MAP) {
			STORE_MAP.remove(store.getId());
		}
	}

	/**
	 * 重新加载
	 */
	public static void reload() {
		synchronized (STORE_MAP) {
			STORE_MAP.clear();
		}
		init();
	}

	public static void main(String[] args) {
		System.out.println(getStoreById(2));
	}
}
