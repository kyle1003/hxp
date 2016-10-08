package cn.hurry.service.loss;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.hurry.data.SessionFactory;
import cn.hurry.data.mapper.goods.GoodsMapper;
import cn.hurry.data.mapper.loss.LossMapper;
import cn.hurry.data.mapper.order.buy.BuyOrderGoodsMapper;
import cn.hurry.manage.goods.GoodsManage;
import cn.hurry.po.goods.Goods;
import cn.hurry.po.loss.Loss;
import cn.hurry.po.order.buy.BuyOrderGoods;

@Service
public class LossService {

	/**
	 * 添加损耗
	 * 
	 * @param Loss
	 *            损耗实例
	 * @return 成功添加数量
	 * @throws Exception
	 *             添加失败异常
	 */
	public void insertLoss(Loss loss) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			loss.setCreateDate(new Date());
			LossMapper lossMapper = session.getMapper(LossMapper.class);
			lossMapper.insertLoss(loss);
			session.commit();
		} finally {
			session.close();
		}
	}

	public int countOrderGoods(HashMap<String, Object> map) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			BuyOrderGoodsMapper buyOrderGoodsMapper = session.getMapper(BuyOrderGoodsMapper.class);
			return buyOrderGoodsMapper.countBuyOrderGoodsByMap(map);
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
	public List<BuyOrderGoods> selectOrderGoods(Map<String, Object> map) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			BuyOrderGoodsMapper buyOrderGoodsMapper = session.getMapper(BuyOrderGoodsMapper.class);
			List<BuyOrderGoods> buyOrderGoods= buyOrderGoodsMapper.selectBuyOrderGoodsByMap(map);
			return buyOrderGoods;
		} finally {
			session.close();
		}
	}

	/**
	 * 根据损耗编号查询损耗
	 * 
	 * @param id
	 *            损耗编号
	 * @return 损耗实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public Loss selectLossById(int id) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			LossMapper lossMapper = session.getMapper(LossMapper.class);
			return lossMapper.selectLossById(id);
		} finally {
			session.close();
		}
	}

	/**
	 * 查询消耗记录
	 * 
	 * @param map
	 *            条件
	 * @return 消耗记录
	 * @throws Exception
	 */
	public List<Loss> selectLossByMap(Map<String, Object> map) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			LossMapper lossMapper = session.getMapper(LossMapper.class);
			return lossMapper.selectLossByMap(map);
		} finally {
			session.close();
		}
	}

	/**
	 * 统计消耗数量
	 * 
	 * @param map
	 *            条件
	 * @return 数量
	 * @throws Exception
	 */
	public int countLossByMap(HashMap<String, Object> map) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			LossMapper lossMapper = session.getMapper(LossMapper.class);
			return lossMapper.countLossByMap(map);
		} finally {
			session.close();
		}
	}

	/**
	 * 修改损耗
	 * 
	 * @param Loss
	 *            损耗实例
	 * @return 成功修改数量
	 * @throws Exception
	 *             修改失败异常
	 */
	public void updateLoss(Loss loss) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			LossMapper lossMapper = session.getMapper(LossMapper.class);
			lossMapper.updateLoss(loss);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * 批量添加损耗信息
	 * 
	 * @param losses
	 *            损耗信息集合
	 * @throws Exception
	 */
	public void insertLosses(List<Loss> losses) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			LossMapper lossMapper = session.getMapper(LossMapper.class);
			BuyOrderGoodsMapper buyOrderGoodsMapper = session.getMapper(BuyOrderGoodsMapper.class);
			GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
			Date date = new Date();
			for (Loss loss : losses) {
				BuyOrderGoods buyOrderGoods = buyOrderGoodsMapper.selectBuyOrderGoodsById(loss.getOrderGoodsId());
				Goods goods = GoodsManage.getGoodsById(buyOrderGoods.getGoodsId());
				double nowNumber = buyOrderGoods.getSurplusNumber() - loss.getNumber();
				if (nowNumber <= 0) {
					throw new Exception(goods.getName() + "库存不足,无法损耗");
				}
				loss.setCreateDate(date);
				// 修改总库存
				goods.setNumber(goods.getNumber() - loss.getNumber());
				goodsMapper.updateGoodsNumber(goods);
				// 修改单据库存
				buyOrderGoods.setSurplusNumber(nowNumber);
				buyOrderGoodsMapper.updateBuyOrderGoods(buyOrderGoods);
				lossMapper.insertLoss(loss);
			}
			session.commit();
			GoodsManage.reload();
		} finally {
			session.close();
		}
	}

}
