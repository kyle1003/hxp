package cn.hurry.manage.unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hurry.po.unit.Norms;
import cn.hurry.service.unit.NormsService;

/**
 * 规格管理类
 * 
 * @author ZhouHao
 * 
 */
public class NormsManage {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(NormsManage.class);

	/**
	 * 规格库
	 */
	private static final HashMap<Integer, Norms> NORMS_MAP = new HashMap<Integer, Norms>();

	static {
		init();
	}

	/**
	 * 加载数据
	 */
	private static void init() {
		try {
			List<Norms> normss = new NormsService().selectNormsByMap(null);
			for (Norms norms : normss) {
				NORMS_MAP.put(norms.getId(), norms);
			}
		} catch (Exception e) {
			logger.error("加载规格失败", e);
		}
	}

	/**
	 * 获取所有规格
	 * 
	 * @return 所有规格
	 */
	public static List<Norms> getAllNorms() {
		synchronized (NORMS_MAP) {
			return new ArrayList<Norms>(NORMS_MAP.values());
		}
	}

	/**
	 * 根据编号获取规格
	 * 
	 * @param id
	 *            规格编号
	 * @return 规格
	 */
	public static Norms getNormsById(int id) {
		for (Norms norms : getAllNorms()) {
			if (norms.getId() == id) {
				return norms;
			}
		}
		return null;
	}

	/**
	 * 根据名称获取规格
	 * 
	 * @param name
	 *            规格名称
	 * @return 规格
	 */
	public static Norms getNormsByName(String name) {
		for (Norms norms : getAllNorms()) {
			if (norms.getName().equals(name)) {
				return norms;
			}
		}
		return null;
	}

	/**
	 * 添加规格
	 * 
	 * @param norms
	 *            规格
	 */
	public static void addNorms(Norms norms) {
		synchronized (NORMS_MAP) {
			NORMS_MAP.put(norms.getId(), norms);
		}
	}

	/**
	 * 删除规格
	 * 
	 * @param norms
	 *            规格
	 */
	public static void removeNorms(Norms norms) {
		synchronized (NORMS_MAP) {
			NORMS_MAP.remove(norms.getId());
		}
	}

	/**
	 * 重新加载
	 */
	public static void reload() {
		synchronized (NORMS_MAP) {
			NORMS_MAP.clear();
		}
		init();
	}

}
