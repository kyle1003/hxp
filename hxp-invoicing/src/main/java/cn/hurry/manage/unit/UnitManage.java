package cn.hurry.manage.unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hurry.po.unit.Unit;
import cn.hurry.service.unit.UnitService;

/**
 * 单位管理类
 * 
 * @author ZhouHao
 * 
 */
public class UnitManage {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(UnitManage.class);

	/**
	 * 单位库
	 */
	private static final HashMap<Integer, Unit> UNIT_MAP = new HashMap<Integer, Unit>();

	static {
		init();
	}

	/**
	 * 加载数据
	 */
	private static void init() {
		try {
			List<Unit> units = new UnitService().selectUnitByMap(null);
			for (Unit unit : units) {
				UNIT_MAP.put(unit.getId(), unit);
			}
		} catch (Exception e) {
			logger.error("加载单位失败", e);
		}
	}

	/**
	 * 获取所有单位
	 * 
	 * @return 所有单位
	 */
	public static List<Unit> getAllUnit() {
		synchronized (UNIT_MAP) {
			return new ArrayList<Unit>(UNIT_MAP.values());
		}
	}

	/**
	 * 根据编号获取单位
	 * 
	 * @param id
	 *            单位编号
	 * @return 单位
	 */
	public static Unit getUnitById(int id) {
		for (Unit unit : getAllUnit()) {
			if (unit.getId() == id) {
				return unit;
			}
		}
		return null;
	}

	/**
	 * 根据名称获取单位
	 * 
	 * @param name
	 *            单位名称
	 * @return 单位
	 */
	public static Unit getUnitByName(String name) {
		for (Unit unit : getAllUnit()) {
			if (unit.getName().equals(name)) {
				return unit;
			}
		}
		return null;
	}

	/**
	 * 添加单位
	 * 
	 * @param unit
	 *            单位
	 */
	public static void addUnit(Unit unit) {
		synchronized (UNIT_MAP) {
			UNIT_MAP.put(unit.getId(), unit);
		}
	}

	/**
	 * 删除单位
	 * 
	 * @param unit
	 *            单位
	 */
	public static void removeUnit(Unit unit) {
		synchronized (UNIT_MAP) {
			UNIT_MAP.remove(unit.getId());
		}
	}

	/**
	 * 重新加载
	 */
	public static void reload() {
		synchronized (UNIT_MAP) {
			UNIT_MAP.clear();
		}
		init();
	}

}
