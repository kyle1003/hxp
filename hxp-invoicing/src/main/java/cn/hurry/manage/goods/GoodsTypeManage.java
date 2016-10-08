package cn.hurry.manage.goods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hurry.po.goods.GoodsType;
import cn.hurry.service.goods.GoodsTypeService;

/**
 * 商品类别管理类
 * 
 * @author ZhouHao
 * 
 */
public class GoodsTypeManage {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(GoodsTypeManage.class);

	/**
	 * 商品类别库
	 */
	private static final HashMap<Integer, GoodsType> GOODS_TYPE_MAP = new HashMap<Integer, GoodsType>();

	static {
		init();
	}

	/**
	 * 加载数据
	 */
	private static void init() {
		try {
			List<GoodsType> goodsTypes = new GoodsTypeService().selectGoodsTypeByMap(null);
			for (GoodsType goodsType : goodsTypes) {
				GOODS_TYPE_MAP.put(goodsType.getId(), goodsType);
			}
		} catch (Exception e) {
			logger.error("加载商品类别失败", e);
		}
	}

	/**
	 * 获取所有商品类别
	 * 
	 * @return 所有商品类别
	 */
	public static List<GoodsType> getAllGoodsType() {
		synchronized (GOODS_TYPE_MAP) {
			List<GoodsType> list = new ArrayList<GoodsType>(GOODS_TYPE_MAP.values());
			Collections.sort(list);
			return list;
		}
	}

	/**
	 * 根据编号获取商品类别
	 * 
	 * @param id
	 *            商品类别编号
	 * @return 商品类别
	 */
	public static GoodsType getGoodsTypeById(int id) {
		for (GoodsType goodsType : getAllGoodsType()) {
			if (goodsType.getId() == id) {
				return goodsType;
			}
		}
		return null;
	}

	/**
	 * 根据名称获取商品类别
	 * 
	 * @param name
	 *            商品类别名称
	 * @return 商品类别
	 */
	public static GoodsType getGoodsTypeByName(String name) {
		for (GoodsType goodsType : getAllGoodsType()) {
			if (goodsType.getName().equals(name)) {
				return goodsType;
			}
		}
		return null;
	}


	/**
	 * 根据父级编号获取子级商品类别
	 * 
	 * @param pid
	 *            商品类别父级编号
	 * @return 商品类别
	 */
	public static List<GoodsType> getGoodsTypeByPid(int pid, boolean all) {
		List<GoodsType> goodsTypes = new ArrayList<GoodsType>();
		for (GoodsType goodsType : getAllGoodsType()) {
			if (goodsType.getPid() == pid) {
				goodsTypes.add(goodsType);
				if (all) {
					goodsTypes.addAll(getGoodsTypeByPid(goodsType.getId(), all));
				}
			}
		}
		return goodsTypes;
	}

	/**
	 * 添加商品类别
	 * 
	 * @param goodsType
	 *            商品类别
	 */
	public static void addGoodsType(GoodsType goodsType) {
		synchronized (GOODS_TYPE_MAP) {
			GOODS_TYPE_MAP.put(goodsType.getId(), goodsType);
		}
	}

	/**
	 * 删除商品类别
	 * 
	 * @param goodsType
	 *            商品类别
	 */
	public static void removeGoodsType(GoodsType goodsType) {
		synchronized (GOODS_TYPE_MAP) {
			GOODS_TYPE_MAP.remove(goodsType.getId());
		}
	}

	/**
	 * 重新加载
	 */
	public static void reload() {
		synchronized (GOODS_TYPE_MAP) {
			GOODS_TYPE_MAP.clear();
		}
		init();
	}

}
