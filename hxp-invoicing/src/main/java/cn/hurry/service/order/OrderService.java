package cn.hurry.service.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.hurry.data.SessionFactory;
import cn.hurry.data.mapper.goods.GoodsMapper;
import cn.hurry.data.mapper.order.buy.BuyOrderGoodsMapper;
import cn.hurry.data.mapper.order.buy.BuyOrderMapper;
import cn.hurry.data.mapper.order.giveback.GiveBackBuyOrderGoodsMapper;
import cn.hurry.data.mapper.order.giveback.GiveBackBuyOrderMapper;
import cn.hurry.data.mapper.order.giveback.GiveBackSellOrderGoodsMapper;
import cn.hurry.data.mapper.order.giveback.GiveBackSellOrderMapper;
import cn.hurry.data.mapper.order.sell.SellInfoMapper;
import cn.hurry.data.mapper.order.sell.SellOrderGoodsMapper;
import cn.hurry.data.mapper.order.sell.SellOrderMapper;
import cn.hurry.data.mapper.plan.SellPlanMapper;
import cn.hurry.data.mapper.succession.SuccessionMapper;
import cn.hurry.manage.goods.GoodsManage;
import cn.hurry.manage.store.StoreManage;
import cn.hurry.manage.succession.SuccessionManage;
import cn.hurry.manage.unit.UnitManage;
import cn.hurry.po.goods.Goods;
import cn.hurry.po.order.Order;
import cn.hurry.po.order.OrderGoods;
import cn.hurry.po.order.buy.BuyOrder;
import cn.hurry.po.order.buy.BuyOrderGoods;
import cn.hurry.po.order.giveback.GiveBackBuyOrder;
import cn.hurry.po.order.giveback.GiveBackSellOrder;
import cn.hurry.po.order.sell.SellInfo;
import cn.hurry.po.order.sell.SellOrder;
import cn.hurry.po.order.sell.SellOrderGoods;
import cn.hurry.po.plan.SellPlan;
import cn.hurry.po.succession.Succession;
import cn.hurry.util.IdCreater;
import cn.hurry.util.NumberUtil;

@Service
public class OrderService {

	public String addCheckOutOrder(Order order) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SuccessionMapper successionMapper = session.getMapper(SuccessionMapper.class);
			// 生成id
			String id = createId(session, order);
			if (id == null)
				throw new Exception("ID生成失败");
			order.setId(id);
			order.setCreateDate(new Date());
			for (OrderGoods orderGoods : order.getOrderGoods()) {
				orderGoods.setOrderId(order.getId());
				insertOrderGoods(session, orderGoods, order);
			}
			// 添加销售单
			if (order.getType() == Order.TYPE_SELL_ORDER) {
				SellOrderMapper sellOrderMapper = session.getMapper(SellOrderMapper.class);
				sellOrderMapper.insertSellOrder(order);
				// 商品出库
				goodsOutWarehouse(session, order);
			}
			// 更新交接班信息
			Succession succession = SuccessionManage.getSuccessionByStatus(Succession.STATUS_WORKING);
			succession.setSellOrderIds(id);
			successionMapper.updateSuccession(succession);
			SuccessionManage.put(succession);
			session.commit();
			return id;
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
			IdCreater.getInstance().unlock();
		}
	}

	public void updateOrder(Order order) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			Order dborder = selectOrderById(order.getId());
			if (dborder.getStatus() == Order.STATUS_THROUGHCHECK)
				throw new Exception("该单据已生效,无法修改");
			order.setStoreId(dborder.getStoreId());
			order.setPay(dborder.getPay());
			// 原始状态
			switch (dborder.getStatus()) {
			// 未审核
			case Order.STATUS_NOTCHECKED:
				// 目标状态
				switch (order.getStatus()) {
				// 通过审核 （在未审核的情况下，只有通过审核会改变其库存量）
				case Order.STATUS_THROUGHCHECK:
					switch (dborder.getType()) {// 订单类型
					case Order.TYPE_BUY_ORDER:// 采购单审核通过（ 商品入库）
						goodsBackWarehouse(session, order);
						break;
					case Order.TYPE_SELL_RETURN_ORDER:// 销售退货单审核通过（ 商品入库）
						goodsBackWarehouse(session, order);
						break;
					case Order.TYPE_SELL_ORDER:// 销售单审核通过（ 商品出库）
						goodsOutWarehouse(session, order);
						break;
					case Order.TYPE_BUY_RETURN_ORDER:// 采购退货单审核通过（ 商品出库）
						goodsOutWarehouse(session, order);
						break;
					default:
						break;
					}
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
			if (order.getType() == Order.TYPE_BUY_ORDER) {
				BuyOrderMapper buyOrderMapper = session.getMapper(BuyOrderMapper.class);
				buyOrderMapper.updateBuyOrder(order);
			} else if (order.getType() == Order.TYPE_SELL_ORDER) {
				SellOrderMapper sellOrderMapper = session.getMapper(SellOrderMapper.class);
				sellOrderMapper.updateSellOrder(order);
			} else if (order.getType() == Order.TYPE_SELL_RETURN_ORDER) {
				GiveBackSellOrderMapper giveBackSellOrderMapper = session.getMapper(GiveBackSellOrderMapper.class);
				giveBackSellOrderMapper.updateGiveBackSellOrder(order);
			} else if (order.getType() == Order.TYPE_BUY_RETURN_ORDER) {
				GiveBackBuyOrderMapper giveBackBuyOrderMapper = session.getMapper(GiveBackBuyOrderMapper.class);
				giveBackBuyOrderMapper.updateGiveBackBuyOrder(order);
			}
			session.commit();
		} finally {
			session.close();
			// id使用完成后解锁
			IdCreater.getInstance().unlock();
			// 刷新库存
			GoodsManage.reload();
		}
	}

	/**
	 * 商品归库
	 * 
	 * @param session
	 *            SqlSession
	 * @param order
	 *            订单实例
	 * @throws Exception
	 */
	public void goodsBackWarehouse(SqlSession session, Order order) throws Exception {
		BuyOrderGoodsMapper orderGoodsMapper = session.getMapper(BuyOrderGoodsMapper.class);
		GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
		List<BuyOrderGoods> buyOrderGoods = orderGoodsMapper.selectBuyOrderGoodsByOrderId(order.getId());
		for (BuyOrderGoods orderGoods : buyOrderGoods) {
			Goods goods = GoodsManage.getGoodsById(orderGoods.getGoodsId());
			double number = orderGoods.getNumber();
			goods.setNumber(NumberUtil.convert(goods.getNumber() + number));
			goodsMapper.updateGoodsNumber(goods);
		}
	}

	/**
	 * 商品出库 (商品销售审核时出库)
	 * 
	 * @param session
	 *            SqlSession
	 * @param order
	 *            订单实例
	 * @throws Exception
	 */
	private void goodsOutWarehouse(SqlSession session, Order order) throws Exception {
		boolean autoSubmit = false;
		if (session == null) {
			session = SessionFactory.openSession();
			autoSubmit = true;
		}
		SellOrderGoodsMapper sellOrderGoodsMapper = session.getMapper(SellOrderGoodsMapper.class);
		BuyOrderGoodsMapper buyOrderGoodsMapper = session.getMapper(BuyOrderGoodsMapper.class);
		GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
		SellInfoMapper sellInfoMapper = session.getMapper(SellInfoMapper.class);
		SellPlanMapper planMapper = session.getMapper(SellPlanMapper.class);
		List<SellOrderGoods> sellOrderGoods = sellOrderGoodsMapper.selectSellOrderGoodsByOrderId(order.getId());
		// 成本核算方案
		byte plan = planMapper.selectSellPlan();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("sortField", "createDate");
		// 后进先出 倒叙排列 默认先进先出
		if (plan == SellPlan.TYPE_LASTTIN_FRISTOUT)
			map.put("sortOrder", "DESC");
		map.put("surplusNumberNot", 0);
		//忽略仓库
		// map.put("storeId", order.getStoreId());
		map.put("status", Order.STATUS_THROUGHCHECK);
		List<Goods> goodsList = new ArrayList<Goods>();
		for (OrderGoods orderGoods : sellOrderGoods) {
			Goods goods = GoodsManage.getGoodsById(orderGoods.getGoodsId());
			double number = orderGoods.getNumber();// 出货数量
			// 总库存剩余
			double nowNumber = NumberUtil.convert(goods.getNumber() - number);
			map.put("goodsId", goods.getId());
			List<BuyOrderGoods> buyOrderGoodsList = buyOrderGoodsMapper.selectBuyOrderGoodsByMap(map);
			if (nowNumber < 0)// 总库存不足
				throw new Exception("商品:" + goods.getName() + ",库存不足!剩余:" + goods.getNumber() + (UnitManage.getUnitById(goods.getUnitId()).getName()));
			if (buyOrderGoodsList.size() == 0)// 仓库库存不足
				throw new Exception("仓库:" + (StoreManage.getStoreById(order.getStoreId()).getName()) + "中商品:" + goods.getName() + ",库存不足!");
			double tempNumber = number;
			double xqsl = tempNumber;
			for (int i = 0, size = buyOrderGoodsList.size(); i < size; i++) {
				BuyOrderGoods buyOrderGoods = buyOrderGoodsList.get(i);
				// 采购单剩余数量=出货前剩余数量-已出货数量
				double surplusNumber = tempNumber = buyOrderGoods.getSurplusNumber() - Math.abs(tempNumber);
				// 销售详情记录
				SellInfo sellInfo = new SellInfo();
				sellInfo.setBuyOrderId(buyOrderGoods.getOrderId());
				sellInfo.setSellOrderId(orderGoods.getOrderId());
				sellInfo.setBuyOrderGoodsId(buyOrderGoods.getId());
				sellInfo.setSellOrderGoodsId(orderGoods.getId());
				// 设置销售详情，使用本次采购单后采购单商品的剩余数量.负数则未本次不够将进入下一单消耗
				sellInfo.setSurplusNumber(NumberUtil.convert(surplusNumber));
				if (surplusNumber >= 0) {// 本次采购单未销售完
					sellInfo.setRemark("本次采购单未销售完");
					sellInfo.setNumber(Math.abs(xqsl));
					buyOrderGoods.setSurplusNumber(NumberUtil.convert(surplusNumber));
					buyOrderGoodsMapper.updateBuyOrderGoods(buyOrderGoods);
					sellInfoMapper.insertSellInfo(sellInfo);
					break;
				} else {// 本次采购单已销售完,进入下一个采购单
					// 无更多采购单，无法销售
					if (i == size - 1)
						throw new Exception("采购单:[" + buyOrderGoods.getOrderId() + "]商品[" + goods.getName() + "] 数量不足,且未找到更多采购单!");
					sellInfo.setRemark("本次采购单已销售完，使用下一销售单!");
					// sellInfo.setSurplusNumber(0);
					sellInfo.setNumber(NumberUtil.convert(buyOrderGoods.getSurplusNumber()));
					buyOrderGoods.setSurplusNumber(0);
					buyOrderGoodsMapper.updateBuyOrderGoods(buyOrderGoods);
					sellInfoMapper.insertSellInfo(sellInfo);
					xqsl = surplusNumber;
				}
			}
			goods.setNumber(nowNumber);
			goodsMapper.updateGoodsNumber(goods);
			goodsList.add(goods);
		}
		if (autoSubmit) {
			session.commit();
			session.close();
		}
	}

	/**
	 * 创建ID
	 * 
	 * @param session
	 *            SqlSession
	 * @param order
	 *            单据
	 * @return ID
	 * @throws Exception
	 */
	private String createId(SqlSession session, Order order) throws Exception {
		if (order instanceof BuyOrder || order.getType() == Order.TYPE_BUY_ORDER) {
			BuyOrderMapper buyOrderMapper = session.getMapper(BuyOrderMapper.class);
			String maxId = buyOrderMapper.selectMaxId();
			return IdCreater.getInstance().createId(Order.ID_KEY_BUY, maxId);
		} else if (order instanceof SellOrder || order.getType() == Order.TYPE_SELL_ORDER) {
			SellOrderMapper sellOrderMapper = session.getMapper(SellOrderMapper.class);
			String maxId = sellOrderMapper.selectMaxId();
			return IdCreater.getInstance().createId(Order.ID_KEY_SEL, maxId);
		} else if (order instanceof GiveBackSellOrder || order.getType() == Order.TYPE_SELL_RETURN_ORDER) {
			GiveBackSellOrderMapper giveBackSellOrderMapper = session.getMapper(GiveBackSellOrderMapper.class);
			String maxId = giveBackSellOrderMapper.selectMaxId();
			return IdCreater.getInstance().createId(Order.ID_KEY_SEL_RETURN, maxId);
		} else if (order instanceof GiveBackBuyOrder || order.getType() == Order.TYPE_BUY_RETURN_ORDER) {
			GiveBackBuyOrderMapper giveBackBuyOrderMapper = session.getMapper(GiveBackBuyOrderMapper.class);
			String maxId = giveBackBuyOrderMapper.selectMaxId();
			return IdCreater.getInstance().createId(Order.ID_KEY_BUY_RETURN, maxId);
		}
		return null;
	}

	/**
	 * 添加订单商品绑定对象
	 * 
	 * @param session
	 *            SqlSession
	 * @param orderGoods
	 *            订单商品绑定对象
	 * @throws Exception
	 */
	private void insertOrderGoods(SqlSession session, OrderGoods orderGoods, Order order) throws Exception {
		if (order.getType() == Order.TYPE_BUY_ORDER) {
			BuyOrderGoodsMapper buyOrderGoodsMapper = session.getMapper(BuyOrderGoodsMapper.class);
			buyOrderGoodsMapper.insertBuyOrderGoods(orderGoods);
		} else if (order.getType() == Order.TYPE_SELL_ORDER) {
			SellOrderGoodsMapper sellOrderGoodsMapper = session.getMapper(SellOrderGoodsMapper.class);
			sellOrderGoodsMapper.insertSellOrderGoods(orderGoods);
		} else if (order.getType() == Order.TYPE_SELL_RETURN_ORDER) {
			GiveBackBuyOrderGoodsMapper giveBackBuyOrderGoodsMapper = session.getMapper(GiveBackBuyOrderGoodsMapper.class);
			giveBackBuyOrderGoodsMapper.insertGiveBackBuyOrderGoods(orderGoods);
		} else if (order.getType() == Order.TYPE_BUY_RETURN_ORDER) {
			GiveBackSellOrderGoodsMapper giveBackSellOrderGoodsMapper = session.getMapper(GiveBackSellOrderGoodsMapper.class);
			giveBackSellOrderGoodsMapper.insertGiveBackSellOrderGoods(orderGoods);
		}
	}

	/**
	 * 添加单据
	 * 
	 * @param Order
	 *            单据实例
	 * @throws Exception
	 *             添加失败异常
	 */
	public String insertOrder(Order order) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			// 生成id
			String id = createId(session, order);
			if (id == null)
				throw new Exception("ID生成失败");
			order.setId(id);
			order.setCreateDate(new Date());
			for (OrderGoods orderGoods : order.getOrderGoods()) {
				orderGoods.setOrderId(order.getId());
				insertOrderGoods(session, orderGoods, order);
			}
			// 添加采购单
			if (order.getType() == Order.TYPE_BUY_ORDER) {
				BuyOrderMapper buyOrderMapper = session.getMapper(BuyOrderMapper.class);
				buyOrderMapper.insertBuyOrder(order);
				// 商品归库
				// goodsBackWarehouse(session, order);
			}
			// 添加销售单
			else if (order.getType() == Order.TYPE_SELL_ORDER) {
				SellOrderMapper sellOrderMapper = session.getMapper(SellOrderMapper.class);
				sellOrderMapper.insertSellOrder(order);
				// 商品出库
				// goodsOutWarehouse(session, order);
			}
			// 添加销售退货单
			else if (order.getType() == Order.TYPE_SELL_RETURN_ORDER) {
				GiveBackSellOrderMapper giveBackSellOrderMapper = session.getMapper(GiveBackSellOrderMapper.class);
				giveBackSellOrderMapper.insertGiveBackSellOrder(order);
				// 商品归库
				// goodsBackWarehouse(session, order);
			}
			// 添加采购退货单
			else if (order.getType() == Order.TYPE_BUY_RETURN_ORDER) {
				GiveBackBuyOrderMapper giveBackBuyOrderMapper = session.getMapper(GiveBackBuyOrderMapper.class);
				giveBackBuyOrderMapper.insertGiveBackBuyOrder(order);
				// 商品出库
				// goodsOutWarehouse(session, order);
			}
			session.commit();
			return id;
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
			// id使用完成后解锁
			IdCreater.getInstance().unlock();
		}

	}

	/**
	 * 根据单据编号查询单据
	 * 
	 * @param id
	 *            单据编号
	 * @return 单据实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public Order selectOrderById(String id) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			BuyOrderMapper buyOrderMapper = session.getMapper(BuyOrderMapper.class);
			SellOrderMapper sellOrderMapper = session.getMapper(SellOrderMapper.class);
			GiveBackBuyOrderMapper giveBackBuyOrderMapper = session.getMapper(GiveBackBuyOrderMapper.class);
			GiveBackSellOrderMapper giveBackSellOrderMapper = session.getMapper(GiveBackSellOrderMapper.class);
			Order order = buyOrderMapper.selectBuyOrderById(id);
			if (order == null)
				order = sellOrderMapper.selectSellOrderById(id);
			if (order == null)
				order = giveBackBuyOrderMapper.selectGiveBackBuyOrderById(id);
			if (order == null)
				order = giveBackSellOrderMapper.selectGiveBackSellOrderById(id);
			return order;
		} finally {
			session.close();
		}
	}

	/**
	 * 根据map条件集合查询单据
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的单据集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<Order> selectOrderByMap(HashMap<String, Object> map) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			List<Order> orders = new ArrayList<Order>();
			BuyOrderMapper buyOrderMapper = session.getMapper(BuyOrderMapper.class);
			SellOrderMapper sellOrderMapper = session.getMapper(SellOrderMapper.class);
			GiveBackBuyOrderMapper giveBackBuyOrderMapper = session.getMapper(GiveBackBuyOrderMapper.class);
			GiveBackSellOrderMapper giveBackSellOrderMapper = session.getMapper(GiveBackSellOrderMapper.class);
			Object obj = map.get("type");
			Order order = new Order();
			order.setId("合计");
			if (obj != null) {
				switch (Integer.parseInt(obj.toString())) {
				case Order.TYPE_BUY_ORDER:
					orders.addAll(buyOrderMapper.selectBuyOrderByMap(map));
					try {
						order.setPay(buyOrderMapper.sumBuyOrderByMap(map));
					} catch (Exception e) {
						order.setPay(0);
					}
					if (orders.size() > 0)
						orders.add(order);
					break;
				case Order.TYPE_SELL_ORDER:
					orders.addAll(sellOrderMapper.selectSellOrderByMap(map));
					try {
						order.setPay(sellOrderMapper.sumSellOrderByMap(map));
					} catch (Exception e) {
						order.setPay(0);
					}
					if (orders.size() > 0)
						orders.add(order);
					break;
				case Order.TYPE_BUY_RETURN_ORDER:
					orders.addAll(giveBackBuyOrderMapper.selectGiveBackBuyOrderByMap(map));
					break;
				case Order.TYPE_SELL_RETURN_ORDER:
					orders.addAll(giveBackSellOrderMapper.selectGiveBackSellOrderByMap(map));
					break;
				default:
					break;
				}
			} else {
				orders.addAll(buyOrderMapper.selectBuyOrderByMap(map));
				orders.addAll(sellOrderMapper.selectSellOrderByMap(map));
				orders.addAll(giveBackBuyOrderMapper.selectGiveBackBuyOrderByMap(map));
				orders.addAll(giveBackSellOrderMapper.selectGiveBackSellOrderByMap(map));
			}
			return orders;
		} finally {
			session.close();
		}
	}

	public int countOrderByMap(HashMap<String, Object> map) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			BuyOrderMapper buyOrderMapper = session.getMapper(BuyOrderMapper.class);
			SellOrderMapper sellOrderMapper = session.getMapper(SellOrderMapper.class);
			GiveBackBuyOrderMapper giveBackBuyOrderMapper = session.getMapper(GiveBackBuyOrderMapper.class);
			GiveBackSellOrderMapper giveBackSellOrderMapper = session.getMapper(GiveBackSellOrderMapper.class);
			Object obj = map.get("type");
			if (obj != null) {
				switch (Integer.parseInt(obj.toString())) {
				case Order.TYPE_BUY_ORDER:
					return buyOrderMapper.countBuyOrderByMap(map);
				case Order.TYPE_SELL_ORDER:
					return sellOrderMapper.countSellOrderByMap(map);
				case Order.TYPE_BUY_RETURN_ORDER:
					return giveBackBuyOrderMapper.countGiveBackBuyOrderByMap(map);
				case Order.TYPE_SELL_RETURN_ORDER:
					return giveBackSellOrderMapper.countGiveBackSellOrderByMap(map);
				default:
					return 0;
				}
			} else {
				return buyOrderMapper.countBuyOrderByMap(map) + sellOrderMapper.countSellOrderByMap(map)
						+ giveBackBuyOrderMapper.countGiveBackBuyOrderByMap(map) + giveBackSellOrderMapper.countGiveBackSellOrderByMap(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}
	}

	public List<BuyOrderGoods> selectOrderGoodsByMap(Map<String, Object> map) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			BuyOrderGoodsMapper buyOrderGoodsMapper = session.getMapper(BuyOrderGoodsMapper.class);
			return buyOrderGoodsMapper.selectBuyOrderGoodsByMap(map);
		} finally {
			session.close();
		}
	}

	public void updateOrderInfo(Order order) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			switch (order.getType()) {
			case Order.TYPE_BUY_ORDER:
				BuyOrderMapper buyOrderMapper = session.getMapper(BuyOrderMapper.class);
				BuyOrderGoodsMapper buyOrderGoodsMapper = session.getMapper(BuyOrderGoodsMapper.class);
				buyOrderMapper.updateBuyOrder(order);
				for (OrderGoods orderGoods : order.getOrderGoods()) {
					buyOrderGoodsMapper.updateBuyOrderGoods(orderGoods);
				}
				break;
			case Order.TYPE_SELL_ORDER:
				SellOrderGoodsMapper sellOrderGoodsMapper = session.getMapper(SellOrderGoodsMapper.class);
				SellOrderMapper sellOrderMapper = session.getMapper(SellOrderMapper.class);
				sellOrderMapper.updateSellOrder(order);
				for (OrderGoods orderGoods : order.getOrderGoods()) {
					sellOrderGoodsMapper.updateSellOrderGoods(orderGoods);
				}
				break;
			default:
				break;
			}
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * 根据条件集合查询销售单明细
	 * 
	 * @param map条件集合
	 * @return 销售单明细集合
	 * @throws Exception
	 */
	public List<SellInfo> selectSellInfoByMap(HashMap<String, Object> map) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SellInfoMapper infoMapper = session.getMapper(SellInfoMapper.class);
			return infoMapper.selectSellInfoByMap(map);
		} finally {
			session.close();
		}
	}

}
