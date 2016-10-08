package cn.hurry.service.order;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.hurry.data.SessionFactory;
import cn.hurry.data.mapper.goods.GoodsMapper;
import cn.hurry.data.mapper.order.buy.BuyOrderGoodsMapper;
import cn.hurry.data.mapper.order.giveback.GiveBackInfoMapper;
import cn.hurry.data.mapper.order.sell.SellInfoMapper;
import cn.hurry.data.mapper.order.sell.SellOrderGoodsMapper;
import cn.hurry.data.mapper.order.sell.SellOrderMapper;
import cn.hurry.data.mapper.succession.SuccessionMapper;
import cn.hurry.manage.goods.GoodsManage;
import cn.hurry.po.goods.Goods;
import cn.hurry.po.order.Order;
import cn.hurry.po.order.OrderGoods;
import cn.hurry.po.order.giveback.GiveBackInfo;
import cn.hurry.po.order.sell.SellInfo;
import cn.hurry.po.order.sell.SellOrderGoods;
import cn.hurry.po.succession.Succession;
import cn.hurry.util.NumberUtil;

@Service
public class GiveBackService {

	public int countOrderGoods(HashMap<String, Object> map) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SellOrderGoodsMapper sellOrderGoodsMapper = session.getMapper(SellOrderGoodsMapper.class);
			return sellOrderGoodsMapper.countSellOrderGoodsByMap(map);
		} finally {
			session.close();
		}
	}

	/**
	 * 根据map条件集合查询进货单商品绑定
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的进货单商品绑定集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<SellOrderGoods> selectOrderGoods(HashMap<String, Object> map) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SellOrderGoodsMapper sellOrderGoodsMapper = session.getMapper(SellOrderGoodsMapper.class);
			return sellOrderGoodsMapper.selectSellOrderGoodsByMap(map);
		} finally {
			session.close();
		}
	}

	public int countGiveBackInfoByMap(Map<String, Object> map) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			GiveBackInfoMapper backInfoMapper = session.getMapper(GiveBackInfoMapper.class);
			return backInfoMapper.countGiveBackInfoByMap(map);
		} catch (Exception e) {
			return 0;
		} finally {
			session.close();
		}
	}

	public void insertGiveBackInfo(GiveBackInfo giveBackInfo) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			GiveBackInfoMapper backInfoMapper = session.getMapper(GiveBackInfoMapper.class);
			SellOrderGoodsMapper sellOrderGoodsMapper = session.getMapper(SellOrderGoodsMapper.class);
			SellOrderMapper sellOrderMapper = session.getMapper(SellOrderMapper.class);
			GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
			SuccessionMapper successionMapper = session.getMapper(SuccessionMapper.class);
			BuyOrderGoodsMapper buyOrderGoodsMapper = session.getMapper(BuyOrderGoodsMapper.class);
			Map<String, Object> map = new HashMap<String, Object>();

			SellOrderGoods sellOrderGoods = sellOrderGoodsMapper.selectSellOrderGoodsById(giveBackInfo.getSellOrderGoodsId());
			Order order = sellOrderGoods.getOrder();
			Goods goods = sellOrderGoods.getGoods();
			// 没有收银信息的就不进行操作
			map.put("sellOrderIds", sellOrderGoods.getOrderId());
			List<Succession> list = successionMapper.selectSuccessionByMap(map);
			if (list.size() > 1) {
				throw new Exception("获取收银信息时出错!");
			}
			if (list.size() == 1) {
				Succession succession = list.get(0);
				String oldrmark = succession.getRemark() == null ? "" : succession.getRemark();
				succession.setRemark(oldrmark + " " + sellOrderGoods.getOrderId() + " 中的" + goods.getName() + "退回: " + giveBackInfo.getNumber() + goods.getUnit().getName() + " 原因:" + giveBackInfo.getRemark() + ";");
				successionMapper.updateSuccession(succession);
			}
			// 修改销售单物资数量
			sellOrderGoods.setNumber(sellOrderGoods.getNumber() - giveBackInfo.getNumber());
			sellOrderGoodsMapper.updateSellOrderGoods(sellOrderGoods);
			// 修改库存
			goods.setNumber(goods.getNumber() + giveBackInfo.getNumber());
			goodsMapper.updateGoodsNumber(goods);
			// 修改销售单金额
			order.setPay(NumberUtil.convert(order.getPay() - giveBackInfo.getNumber() * sellOrderGoods.getPrice()));
			sellOrderMapper.updateSellOrder(order);
			giveBackInfo.setBackTime(new Date());
			backInfoMapper.insertGiveBackInfo(giveBackInfo);

			//------------- 采购单库存恢复
			map.clear();
			// 查询出库使用采购单明细表
			map.put("sellOrderGoodsId", sellOrderGoods.getId());
			SellInfoMapper infoMapper = session.getMapper(SellInfoMapper.class);
			List<SellInfo> sellInfos = infoMapper.selectSellInfoByMap(map);
			double number = giveBackInfo.getNumber();
			for (SellInfo sellInfo : sellInfos) {
				if (number <= 0)
					break;
				OrderGoods buyOrderGoods = sellInfo.getBuyOrderGoods();
				double i = buyOrderGoods.getSurplusNumber();
				if (i + number <= buyOrderGoods.getNumber()) {
					buyOrderGoods.setSurplusNumber(i + number);
					number -= i;
				} else {
					buyOrderGoods.setSurplusNumber(buyOrderGoods.getNumber());
					number -= buyOrderGoods.getNumber();
				}
				buyOrderGoodsMapper.updateBuyOrderGoods(buyOrderGoods);
			}
			session.commit();
			GoodsManage.reload();
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	public GiveBackInfo selectGiveBackInfoById(int id) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			GiveBackInfoMapper backInfoMapper = session.getMapper(GiveBackInfoMapper.class);
			return backInfoMapper.selectGiveBackInfoById(id);
		} finally {
			session.close();
		}
	}

	public List<GiveBackInfo> selectGiveBackInfoByMap(Map<String, Object> map) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			GiveBackInfoMapper backInfoMapper = session.getMapper(GiveBackInfoMapper.class);
			return backInfoMapper.selectGiveBackInfoByMap(map);
		} finally {
			session.close();
		}
	}

	public void updateGiveBackInfo(GiveBackInfo giveBackInfo) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			GiveBackInfoMapper backInfoMapper = session.getMapper(GiveBackInfoMapper.class);
			backInfoMapper.updateGiveBackInfo(giveBackInfo);
			session.commit();
		} finally {
			session.close();
		}
	}

}
