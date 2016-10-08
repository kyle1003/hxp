package cn.hurry.service.inventory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.hurry.data.SessionFactory;
import cn.hurry.data.mapper.inventory.InventoryGoodsMapper;
import cn.hurry.data.mapper.inventory.InventoryMapper;
import cn.hurry.data.mapper.order.buy.BuyOrderGoodsMapper;
import cn.hurry.manage.goods.GoodsManage;
import cn.hurry.po.goods.Goods;
import cn.hurry.po.inventory.Inventory;
import cn.hurry.po.inventory.InventoryGoods;
import cn.hurry.po.order.Order;
import cn.hurry.po.order.buy.BuyOrderGoods;
import cn.hurry.util.IdCreater;
import cn.hurry.util.NumberUtil;

@Service
public class InventoryService {
	/**
	 * 删除盘点
	 * 
	 * @param Inventory
	 *            盘点实例
	 * @return 成功删除数量
	 * @throws Exception
	 *             删除失败异常
	 */
	public void deleteInventory(Inventory inventory) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			InventoryMapper inventoryMapper = session.getMapper(InventoryMapper.class);
			inventoryMapper.deleteInventory(inventory);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * 添加盘点
	 * 
	 * @param Inventory
	 *            盘点实例
	 * @return 成功添加数量
	 * @throws Exception
	 *             添加失败异常
	 */
	public void insertInventory(Inventory inventory) throws Exception {
		SqlSession session = SessionFactory.openSession();
		IdCreater creater = IdCreater.getInstance();
		try {
			InventoryMapper inventoryMapper = session.getMapper(InventoryMapper.class);
			InventoryGoodsMapper inventoryGoodsMapper = session.getMapper(InventoryGoodsMapper.class);
			BuyOrderGoodsMapper buyOrderGoodsMapper = session.getMapper(BuyOrderGoodsMapper.class);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("status", Order.STATUS_THROUGHCHECK);//已生效的采购单
			map.put("storeId", inventory.getStoreId());
			List<Goods> goodsList = GoodsManage.getAllGoods();
			String code = creater.createId(Inventory.ID_KEY, inventoryMapper.selectMaxCode());
			inventory.setCode(code);
			inventory.setStatus(Inventory.STATUS_NOT_CHECKED);
			inventory.setCreateDate(new Date());
			inventoryMapper.insertInventory(inventory);
			inventory.setId(inventoryMapper.selectLastIndexId());
			for (Goods goods : goodsList) {
				map.put("goodsId", goods.getId());
				List<BuyOrderGoods> orderGoods = buyOrderGoodsMapper.selectBuyOrderGoodsByMap(map);
				// 此仓库没有对应的商品信息则不做统计
				if (orderGoods.size() > 0) {
					double countNumber = 0.0;// 商品剩余数量
					double money = 0.0;//库存成本
					double sumMoney = 0.0;
					for (BuyOrderGoods buyOrderGoods : orderGoods) {
						sumMoney += buyOrderGoods.getPrice() * buyOrderGoods.getNumber();
						money += buyOrderGoods.getPrice() * buyOrderGoods.getSurplusNumber();
						countNumber += buyOrderGoods.getSurplusNumber();
					}
					InventoryGoods inventoryGoods = new InventoryGoods();
					inventoryGoods.setGoodsId(goods.getId());
					inventoryGoods.setInventoryId(inventory.getId());
					inventoryGoods.setStoreNumber(NumberUtil.convert(countNumber) );
					inventoryGoods.setRealNumber(NumberUtil.convert(countNumber));
					inventoryGoods.setMoney(NumberUtil.convert(money));
					inventoryGoods.setSumMoney(NumberUtil.convert(sumMoney));
					inventoryGoodsMapper.insertInventoryGoods(inventoryGoods);
				}
			}
			session.commit();
		}catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
			creater.unlock();
		}
	}

	/**
	 * 根据盘点编号查询盘点
	 * 
	 * @param id
	 *            盘点编号
	 * @return 盘点实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public Inventory selectInventoryById(int id) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			InventoryMapper inventoryMapper = session.getMapper(InventoryMapper.class);
			return inventoryMapper.selectInventoryById(id);
		} finally {
			session.close();
		}
	}

	/**
	 * 根据map条件集合查询盘点
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的盘点集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<Inventory> selectInventoryByMap(HashMap<String, Object> map) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			InventoryMapper inventoryMapper = session.getMapper(InventoryMapper.class);
			return inventoryMapper.selectInventoryByMap(map);
		} finally {
			session.close();
		}
	}

	public int countInventoryByMap(HashMap<String, Object> map) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			InventoryMapper inventoryMapper = session.getMapper(InventoryMapper.class);
			return inventoryMapper.countInventoryByMap(map);
		} catch (Exception e) {
			return 0;
		} finally {
			session.close();
		}
	}

	public List<InventoryGoods> selectInventoryGoodsByInventoryId(int id) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			InventoryGoodsMapper inventoryGoodsMapper = session.getMapper(InventoryGoodsMapper.class);
			return inventoryGoodsMapper.selectInventoryGoodsByInventoryId(id);
		} finally {
			session.close();
		}
	}

	/**
	 * 修改盘点
	 * 
	 * @param Inventory
	 *            盘点实例
	 * @return 成功修改数量
	 * @throws Exception
	 *             修改失败异常
	 */
	public void updateInventory(Inventory inventory) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			InventoryMapper inventoryMapper = session.getMapper(InventoryMapper.class);
			InventoryGoodsMapper inventoryGoodsMapper = session.getMapper(InventoryGoodsMapper.class);
			Inventory dbinventory = inventoryMapper.selectInventoryById(inventory.getId());
			if (dbinventory.getStatus() == Inventory.STATUS_CHECKED) {
				throw new Exception("该盘点已结转,无法修改");
			}
			inventoryMapper.updateInventory(inventory);
			for (InventoryGoods inventoryGoods : inventory.getInventoryGoods()) {
				inventoryGoodsMapper.updateInventoryGoods(inventoryGoods);
			}
			session.commit();
		} finally {
			session.close();
		}
	}

}
