package cn.hurry.manage.succession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.hurry.manage.Cache;
import cn.hurry.po.succession.Succession;
import cn.hurry.service.succession.SuccessionService;

public class SuccessionManage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8162270557388874886L;

	static {
		init();
	}

	public static void init() {
		SuccessionService successionService = new SuccessionService();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("statusNot", Succession.STATUS_WORKOVER);
			List<Succession> successions = successionService.selectSuccessionByMap(map);
			for (Succession succession : successions) {
				put(succession);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Succession getSuccessionById(int id) {
		return Cache.get(Succession.class, id);
	}

	public static List<Succession> getSuccessionList() {
		return Cache.get(Succession.class);
	}

	public static void clear() {
		Cache.clear(Succession.class);
	}

	public static void remove(int id) {
		Cache.remove(Succession.class, id);
	}

	public static void put(Succession succession) {
		if (succession == null)
			return;
		Cache.put(succession.getId(), succession);
	}

	public static Succession getSuccessionByStatus(byte status) {
		List<Succession> list = Cache.get(Succession.class);
		if (list == null)
			return null;
		for (Succession succession : list) {
			if (succession.getStatus() == status) {
				return succession;
			}
		}
		return null;
	}

	public static List<Succession> getSuccessionByInfoId(int id) {
		List<Succession> list = Cache.get(Succession.class);
		List<Succession> newList = new ArrayList<Succession>();
		if (list == null)
			init();
		list = Cache.get(Succession.class);
		if (list == null)
			return null;
		for (Succession succession : list) {
			if (succession.getSuccessionInfoId() == id) {
				newList.add(succession);
			}
		}
		return newList;
	}
}
