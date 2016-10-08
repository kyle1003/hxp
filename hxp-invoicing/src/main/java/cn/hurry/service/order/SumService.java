package cn.hurry.service.order;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.hurry.data.SessionFactory;
import cn.hurry.data.mapper.order.buy.BuyOrderGoodsMapper;
import cn.hurry.data.mapper.order.sell.SellOrderMapper;
import cn.hurry.po.order.Order;
import cn.hurry.po.order.buy.BuyOrderGoods;
import cn.hurry.util.NumberUtil;

@Service
public class SumService {
	public static final byte SUM_TYPE_ALL = 0;

	public static final byte SUM_TYPE_REAL = 1;

	/**
	 * 统计成本
	 * 
	 * @param type
	 *            SUM_TYPE_ALL 为总成本，SUM_TYPE_REAL为库存成本
	 * @param map 采购单商品 过滤条件 如指定商品 指定采购单状态指定时间段等等
	 * @return 成本
	 * @throws Exception
	 */
	public double sumCost(final byte type, HashMap<String, Object> map) throws Exception {
		double sum = 0.0;
		SqlSession session = SessionFactory.openSession();
		try {
			if (map == null)
				map = new HashMap<String, Object>();
			BuyOrderGoodsMapper buyOrderGoodsMapper = session.getMapper(BuyOrderGoodsMapper.class);
			List<BuyOrderGoods> list = buyOrderGoodsMapper.selectBuyOrderGoodsByMap(map);
			for (BuyOrderGoods order : list) {
				double number = order.getNumber();
				double surplusNumber = order.getSurplusNumber();
				if (type == SUM_TYPE_ALL) {
					sum += number * order.getPrice();
				} else {
					sum += surplusNumber * order.getPrice();
				}
			}
		} finally {
			session.close();
		}
		return NumberUtil.convert(sum);
	}

	/**
	 * 统计销售额
	 * 
	 * @param map根据条件统计销售额
	 * @return
	 */
	public double sumSellPay(HashMap<String, Object> map) {
		SqlSession session = SessionFactory.openSession();
		try {
			SellOrderMapper sellOrderMapper = session.getMapper(SellOrderMapper.class);
			return sellOrderMapper.sumSellOrderByMap(map);
		} catch (Exception e) {
			return 0.0;
		} finally {
			session.close();
		}
	}

	public static void main(String[] args) {
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("status", Order.STATUS_THROUGHCHECK);
			map.put("goodsId",3);
			System.out.println(new SumService().sumCost(SUM_TYPE_REAL,map));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
