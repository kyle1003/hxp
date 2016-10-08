package cn.hurry.manage.goods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hurry.po.goods.Goods;
import cn.hurry.po.goods.GoodsType;
import cn.hurry.po.order.buy.BuyOrderGoods;
import cn.hurry.service.goods.GoodsService;
import cn.hurry.service.loss.LossService;
import cn.hurry.util.JSON;
import cn.hurry.util.StringUtil;

/**
 * 商品管理类
 * 
 * @author ZhouHao
 * 
 */
public class GoodsManage {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(GoodsManage.class);

	/**
	 * 商品库
	 */
	private static final HashMap<Integer, Goods> GOODS_MAP = new HashMap<Integer, Goods>();

	private static final HashMap<String, String> MANUFACTURERS_MAP = new HashMap<String, String>();

	private static final HashMap<String, String> DOSE_MAP = new HashMap<String, String>();

	private static final HashMap<String, String> DOSE_TYPE_MAP = new HashMap<String, String>();

	static {
		init();
	}

	/**
	 * 加载数据
	 */
	private static void init() {
		try {

			List<Goods> goodss = new GoodsService().selectGoodsByMap(null);
			for (Goods goods : goodss) {
				GOODS_MAP.put(goods.getId(), goods);
			}
			init_manufacturers();
			init_dose();
			init_doseType();
		} catch (Exception e) {
			logger.error("加载商品失败", e);
		}
	}

	public static void reloadGoodsNumber() {
		LossService lossService = new LossService();
		try {
			Map<Integer, Double> map = new HashMap<Integer, Double>();
			List<BuyOrderGoods> orderGoodsList = lossService.selectOrderGoods(null);
			for (BuyOrderGoods buyOrderGoods : orderGoodsList) {
				double number = map.get(buyOrderGoods.getGoodsId()) == null ? 0 : map.get(buyOrderGoods.getGoodsId());
				number += buyOrderGoods.getSurplusNumber();
				map.put(buyOrderGoods.getGoodsId(), number);
			}
			List<Goods> goodsList = getAllGoods();
			for (Goods goods : goodsList) {
				double number = map.get(goods.getId()) == null ? goods.getNumber() : map.get(goods.getId());
				goods.setNumber(number);
			}
			new GoodsService().updateGoodsNumber(goodsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取所有商品
	 * 
	 * @return 所有商品
	 */
	public static List<Goods> getAllGoods() {
		synchronized (GOODS_MAP) {
			return new ArrayList<Goods>(GOODS_MAP.values());
		}
	}

	public static List<Goods> getAllGoods(boolean haveBuyOrder) {
		if (!haveBuyOrder)
			return getAllGoods();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("havaByOrder", haveBuyOrder);
		try {
			return new GoodsService().selectGoodsByMap(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据编号获取商品
	 * 
	 * @param id
	 *            商品编号
	 * @return 商品
	 */
	public static Goods getGoodsById(int id) {
		for (Goods goods : getAllGoods()) {
			if (goods.getId() == id) {
				return goods;
			}
		}
		return null;
	}

	/**
	 * 根据名称获取商品
	 * 
	 * @param name
	 *            商品名称
	 * @return 商品
	 */
	public static Goods getGoodsByName(String name) {
		for (Goods goods : getAllGoods()) {
			if (goods.getName().equals(name)) {
				return goods;
			}
		}
		return null;
	}

	/**
	 * 查询商品
	 * 
	 * @param map
	 *            key：goodsTypeId || key
	 * @return 符合条件的商品
	 */
	public static List<Goods> searchGoods(HashMap<String, String> map) {
		String goodsTypeId = map.get("goodsTypeId");
		String key = map.get("key");
		String not0 = map.get("not0");
		String is0 = map.get("is0");
		boolean haveBuyOrder = Boolean.parseBoolean(map.get("haveBuyOrder"));
		List<Goods> ngoodsList = new ArrayList<Goods>();
		List<Goods> goodsList;
		if (StringUtil.isNumber(goodsTypeId)) {
			goodsList = getGoodsByGoodsTypeId(Integer.parseInt(goodsTypeId), haveBuyOrder);
		} else {
			goodsList = getAllGoods(haveBuyOrder);
		}
		if (key == null) {
			return goodsList;
		}
		for (Goods goods : goodsList) {
			goods.setSortField(map.get("sortField"));
			// 全拼
			String qp = StringUtil.getPingYin(goods.getName());
			// 首字母
			String jp = StringUtil.getPinYinHeadChar(goods.getName());
			// 通用名全拼
			String cqp = StringUtil.getPingYin(goods.getMedicineName());
			// 通用名全拼
			String cjp = StringUtil.getPinYinHeadChar(goods.getMedicineName());

			if (goods.getName().contains(key) || goods.getMedicineName().contains(key) || readJgCode(goods.getJgCode()).equals(readJgCode(key))
					|| goods.getName().contains(key) || jp.contains(key.toLowerCase()) || qp.contains(key.toLowerCase()) || key.equals(goods.getCode())
					|| cqp.contains(key) || cjp.contains(key)) {
				if (not0 != null) {
					if (goods.getNumber() > 0) {
						ngoodsList.add(goods);
					}
				} else if (is0 != null) {
					if (goods.getNumber() == 0) {
						ngoodsList.add(goods);
					}
				} else {
					ngoodsList.add(goods);
				}
			}
		}
		String order = map.get("sortOrder");
		if ("desc".equalsIgnoreCase(order)) {
			Collections.sort(ngoodsList, Collections.reverseOrder());
		} else {
			Collections.sort(ngoodsList);
		}
		return ngoodsList;
	}
	
	

	public static List<Goods> getGoodsByPyCode(String code) {
		List<Goods> goodsList = new ArrayList<Goods>();
		for (Goods goods : getAllGoods()) {
			// 商品拼音名称以参数开头 参数转为小写 以匹配大小写
			// 全拼
			String qp = StringUtil.getPingYin(goods.getName());
			// 首字母
			String jp = StringUtil.getPinYinHeadChar(goods.getName());
			// 通用名全拼
			String cqp = StringUtil.getPingYin(goods.getMedicineName());
			// 通用名全拼
			String cjp = StringUtil.getPinYinHeadChar(goods.getMedicineName());
			if (readJgCode(code).equals(readJgCode(goods.getJgCode())) || jp.startsWith(code.toLowerCase()) || qp.startsWith(code.toLowerCase())
					|| code.equals(goods.getCode()) || cqp.contains(code) || cjp.contains(code)) {
				goodsList.add(goods);
			}
		}
		return goodsList;
	}

	public static Goods getGoodsByCode(String code) {
		for (Goods goods : getAllGoods()) {
			if (goods.getCode()!=null&&goods.getCode().equals(code)) {
				return goods;
			}
		}
		return null;
	}

	/**
	 * 根据名称拼音获取商品(全拼) 如 饼干 输入 bing 或者gan
	 * 
	 * @param name
	 *            商品名称拼音简写(全拼)
	 * @return 商品
	 */
	public static List<Goods> getGoodsByPyName(String pYname) {
		List<Goods> goodsList = new ArrayList<Goods>();
		for (Goods goods : getAllGoods()) {
			String py = StringUtil.getPingYin(goods.getName());
			// 商品拼音名称以参数开头 参数转为小写 以匹配大小写
			if (py.contains(pYname.toLowerCase())) {
				goodsList.add(goods);
			}
		}
		return goodsList;
	}

	/**
	 * 根据商品类别编号查询商品
	 * 
	 * @param typeId
	 *            商品类别编号
	 * @return 对应商品集合
	 */
	public static List<Goods> getGoodsByGoodsTypeId(int typeId, boolean haveBuyOrder) {
		List<Goods> goodsList = new ArrayList<Goods>();
		List<GoodsType> goodsTypes = GoodsTypeManage.getGoodsTypeByPid(typeId, true);
		for (Goods goods : getAllGoods(haveBuyOrder)) {
			if (goods.getGoodsTypeId() == typeId) {
				goodsList.add(goods);
			}
			for (GoodsType goodsType : goodsTypes) {
				if (goods.getGoodsTypeId() == goodsType.getId()) {
					goodsList.add(goods);
				}
			}
		}
		return goodsList;
	}

	/**
	 * 根据名称拼音获取商品(首字母) 如 饼干 输入 b 或者bg
	 * 
	 * @param name
	 *            商品名称拼音简写(全拼)
	 * @return 商品
	 */
	public static List<Goods> getGoodsByPyNameHeader(String pYnameHeader) {
		List<Goods> goodsList = new ArrayList<Goods>();
		for (Goods goods : getAllGoods()) {
			String py = StringUtil.getPinYinHeadChar(goods.getName());
			// 商品拼音名称以参数开头 将其转为小写以匹配大小写
			if (py.contains(pYnameHeader.toLowerCase())) {
				goodsList.add(goods);
			}
		}
		return goodsList;
	}

	/**
	 * 添加商品
	 * 
	 * @param goods
	 *            商品
	 */
	public static void addGoods(Goods goods) {
		synchronized (GOODS_MAP) {
			GOODS_MAP.put(goods.getId(), goods);
		}
		init_manufacturers();
		init_dose();
		init_doseType();
	}

	/**
	 * 删除商品
	 * 
	 * @param goods
	 *            商品
	 */
	public static void removeGoods(Goods goods) {
		synchronized (GOODS_MAP) {
			GOODS_MAP.remove(goods.getId());
		}
	}

	/**
	 * 重新加载
	 */
	public static void reload() {
		synchronized (GOODS_MAP) {
			GOODS_MAP.clear();
		}
		init();
	}

	public static void init_manufacturers() {
		synchronized (MANUFACTURERS_MAP) {
			for (Goods goods : getAllGoods()) {
				MANUFACTURERS_MAP.put(goods.getManufacturers(), goods.getManufacturers());
			}
		}
	}

	public static void init_dose() {
		synchronized (DOSE_MAP) {
			for (Goods goods : getAllGoods()) {
				DOSE_MAP.put(goods.getDose(), goods.getDose());
			}
		}
	}

	public static void init_doseType() {
		synchronized (DOSE_TYPE_MAP) {
			for (Goods goods : getAllGoods()) {
				DOSE_TYPE_MAP.put(goods.getDoseType(), goods.getDoseType());
			}
		}
	}

	public static String getManufacturersJson() {
		List<Map<String, String>> jsonlst = new ArrayList<Map<String, String>>();
		for (String value : MANUFACTURERS_MAP.values()) {
			if (!StringUtil.isNullOrEmpty(value)) {
				Map<String, String> jsonMap = new HashMap<String, String>();
				String qp = StringUtil.getPinYinHeadChar(value);
				jsonMap.put("text", value);
				jsonMap.put("id", value);
				jsonMap.put("pinyin", qp);
				jsonlst.add(jsonMap);
			}
		}
		return JSON.Encode(jsonlst);
	}

	public static String getDoseJson() {
		List<Map<String, String>> jsonlst = new ArrayList<Map<String, String>>();
		for (String value : DOSE_MAP.values()) {
			if (!StringUtil.isNullOrEmpty(value)) {
				Map<String, String> jsonMap = new HashMap<String, String>();
				jsonMap.put("text", value);
				jsonMap.put("id", value);
				jsonlst.add(jsonMap);
			}
		}
		return JSON.Encode(jsonlst);
	}

	public static String getDoseTypeJson() {
		List<Map<String, String>> jsonlst = new ArrayList<Map<String, String>>();
		for (String value : DOSE_TYPE_MAP.values()) {
			if (!StringUtil.isNullOrEmpty(value)) {
				Map<String, String> jsonMap = new HashMap<String, String>();
				jsonMap.put("text", value);
				jsonMap.put("id", value);
				jsonlst.add(jsonMap);
			}
		}
		return JSON.Encode(jsonlst);
	}

	public static String readJgCode(String code) {
		if (code == null)
			return "";
		if (code.length() < 7)
			return code;
		return code.substring(0, 7);
	}

}
