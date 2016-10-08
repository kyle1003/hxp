package cn.hurry.service.goods;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.hurry.data.SessionFactory;
import cn.hurry.data.mapper.goods.GoodsMapper;
import cn.hurry.data.mapper.loss.LossMapper;
import cn.hurry.data.mapper.order.buy.BuyOrderMapper;
import cn.hurry.data.mapper.order.sell.SellInfoMapper;
import cn.hurry.data.mapper.order.sell.SellOrderMapper;
import cn.hurry.data.mapper.unit.NormsMapper;
import cn.hurry.data.mapper.unit.UnitMapper;
import cn.hurry.data.mapper.user.UserMapper;
import cn.hurry.manage.goods.GoodsManage;
import cn.hurry.manage.unit.NormsManage;
import cn.hurry.manage.unit.UnitManage;
import cn.hurry.po.goods.Goods;
import cn.hurry.po.goods.InvoicingInfo;
import cn.hurry.po.unit.Norms;
import cn.hurry.po.unit.Unit;

@Service
public class GoodsService {

	public static void main(String[] args) {
		try {
			new GoodsService().batchAdd(new File("C:\\Users\\ZhuQingShan\\Desktop\\药品基础数据.xls"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量导入员工
	 * 
	 * @param multipartFile
	 *            导入文件
	 * @throws Exception
	 */
	public String batchAdd(File file) throws Exception {
		SqlSession session = SessionFactory.openSession();
		Workbook wb = Workbook.getWorkbook(new FileInputStream(file));
		int successNumber = 0;
		int errorNumber = 0;
		try {
			NormsMapper normsMapper = session.getMapper(NormsMapper.class);
			UserMapper userMapper = session.getMapper(UserMapper.class);
			UnitMapper unitMapper = session.getMapper(UnitMapper.class);
			GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
			Sheet unitSheet = wb.getSheet(0);
			int unitRows = unitSheet.getRows();
			// 从第二行开始录入（第一行为表头）
			for (int i = 1; i < unitRows; i++) {
				Goods goods = new Goods();
				Cell[] cells = unitSheet.getRow(i);
				String medicineName = cells[0].getContents();
				String name = cells[1].getContents();
				String doseType = cells[2].getContents();
				String norms = cells[3].getContents();
				String unit = cells[4].getContents();
				String buyPrice = cells[5].getContents();
				String manufacturers = cells[6].getContents();

				Norms norms2 = NormsManage.getNormsByName(norms.trim());
				Unit unit2 = UnitManage.getUnitByName(unit.trim());
				if (norms2 == null) {
					norms2 = new Norms();
					norms2.setName(norms);
					normsMapper.insertNorms(norms2);
					norms2.setId(userMapper.selectLastInsertId());
					NormsManage.addNorms(norms2);
				}
				if (unit2 == null) {
					unit2 = new Unit();
					unit2.setName(unit);
					unitMapper.insertUnit(unit2);
					unit2.setId(userMapper.selectLastInsertId());
					UnitManage.addUnit(unit2);
				}
				goods.setBuyPrice(Double.parseDouble(buyPrice));
				goods.setMedicineName(medicineName);
				goods.setName(name);
				goods.setDoseType(doseType);
				goods.setNormsId(norms2.getId());
				goods.setUnit(unit2);
				goods.setUnitId(unit2.getId());
				goods.setNorms(norms2);
				goods.setSellPrice(Double.parseDouble(buyPrice));
				goods.setManufacturers(manufacturers);
				Goods old = GoodsManage.getGoodsByName(name);
				if (old == null || old.getUnitId() != goods.getUnitId() || old.getNormsId() != goods.getNormsId()
						|| !(old.getDose() + "").equals(goods.getDose()) || !(old.getDoseType() + "").equals(goods.getDoseType())
						|| !(old.getManufacturers() + "").equals(goods.getManufacturers())) {
					goodsMapper.insertGoods(goods);
					goods.setId(userMapper.selectLastInsertId());
					GoodsManage.addGoods(goods);
				}
			}
			session.commit();
			GoodsManage.reload();
			UnitManage.reload();
			NormsManage.reload();
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
		}
		return "导入完成:成功导入" + successNumber + "条数据，失败" + errorNumber + "条数据";
	}

	/**
	 * 删除商品
	 * 
	 * @param Goods
	 *            商品实例
	 * @throws Exception
	 *             删除失败异常
	 */
	public void deleteGoods(Goods goods) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			GoodsMapper GoodsMapper = session.getMapper(GoodsMapper.class);
			// 待添加判断商品是否被业务使用模块
			GoodsMapper.deleteGoods(goods);
			session.commit();
			GoodsManage.removeGoods(goods);
		} finally {
			session.close();
		}
	}

	/**
	 * 添加商品
	 * 
	 * @param Goods
	 *            商品实例
	 * @throws Exception
	 *             添加失败异常
	 */
	public void insertGoods(Goods goods) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			GoodsMapper GoodsMapper = session.getMapper(GoodsMapper.class);
			UserMapper userMapper = session.getMapper(UserMapper.class);
			// if (StringUtil.isNullOrEmpty(goods.getCode())) {
			// throw new Exception("条码不能为空！");
			// }
			// Goods dbGoods2 = GoodsManage.getGoodsByCode(goods.getCode());
			// if (dbGoods2 != null) {
			// throw new Exception("条码已存在！");
			// }
			Goods old = GoodsManage.getGoodsByName(goods.getName());
			if (old == null || old.getUnitId() != goods.getUnitId() || old.getNormsId() != goods.getNormsId() || !(old.getDose() + "").equals(goods.getDose())
					|| !(old.getDoseType() + "").equals(goods.getDoseType()) || !(old.getManufacturers() + "").equals(goods.getManufacturers())) {
				GoodsMapper.insertGoods(goods);
				goods.setId(userMapper.selectLastInsertId());
				session.commit();
				GoodsManage.addGoods(goods);
			} else {
				throw new Exception("已存在相同规格型号的商品!");
			}

		} finally {
			session.close();
		}
	}

	/**
	 * 根据商品编号查询商品
	 * 
	 * @param id
	 *            商品编号
	 * @return 商品实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public Goods selectGoodsById(int id) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			GoodsMapper GoodsMapper = session.getMapper(GoodsMapper.class);
			return GoodsMapper.selectGoodsById(id);
		} finally {
			session.close();
		}
	}

	/**
	 * 根据map条件集合查询商品
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的商品集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<Goods> selectGoodsByMap(Map<String, Object> map) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			GoodsMapper GoodsMapper = session.getMapper(GoodsMapper.class);
			return GoodsMapper.selectGoodsByMap(map);
		} finally {
			session.close();
		}
	}

	/**
	 * 修改商品
	 * 
	 * @param Goods
	 *            商品实例
	 * @throws Exception
	 *             修改失败异常
	 */
	public void updateGoods(Goods goods) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			GoodsMapper GoodsMapper = session.getMapper(GoodsMapper.class);
			Goods old = GoodsManage.getGoodsByName(goods.getName());
			if (old != null
					&& (old.getId() != goods.getId() && old.getUnitId() != goods.getUnitId() && old.getNormsId() != goods.getNormsId()
							&& !(old.getDose() + "").equals(goods.getDose()) && !(old.getDoseType() + "").equals(goods.getDoseType()) && !(old
							.getManufacturers() + "").equals(goods.getManufacturers()))) {
				throw new Exception("商品名称已存在");
			}
			GoodsMapper.updateGoods(goods);
			session.commit();
			GoodsManage.reload();
		} finally {
			session.close();
		}
	}

	public List<InvoicingInfo> selectInvoicingInfo(Map<String, Object> map) throws Exception {
		Map<String, Object> selectWhere = new HashMap<String, Object>();
		map.put("havaByOrder", true);

		SqlSession session = SessionFactory.openSession();
		List<InvoicingInfo> list = new LinkedList<InvoicingInfo>();
		try {
			GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
			BuyOrderMapper buyOrder = session.getMapper(BuyOrderMapper.class);
			SellOrderMapper sellOrderMapper = session.getMapper(SellOrderMapper.class);
			SellInfoMapper sellInfoMapper = session.getMapper(SellInfoMapper.class);
			LossMapper lossMapper = session.getMapper(LossMapper.class);
			List<Goods> goodses = goodsMapper.selectGoodsByMap(map);
			for (Goods goods : goodses) {
				goods.setSortField("name");
			}
			Collections.sort(goodses);
			for (Goods goods : goodses) {
				selectWhere.put("goodsId", goods.getId());
				InvoicingInfo invoicingInfo = new InvoicingInfo();
				invoicingInfo.setGoods(goods);
				// 符合时间条件的销售详情
				selectWhere.put("buyCreateDateStart", map.get("buyCreateDateStart"));
				selectWhere.put("buyCreateDateEnd", map.get("buyCreateDateEnd"));
				selectWhere.put("sellCreateDateStart", map.get("sellCreateDateStart"));
				selectWhere.put("sellCreateDateEnd", map.get("sellCreateDateEnd"));
				invoicingInfo.setSellInfos(sellInfoMapper.selectSellInfoByMap(selectWhere));
				selectWhere.put("sortField", "id");
				// 符合时间条件的采购单
				selectWhere.put("createDateStart", map.get("buyCreateDateStart"));
				selectWhere.put("createDateEnd", map.get("buyCreateDateEnd"));
				invoicingInfo.setBuyOrders(buyOrder.selectBuyOrderByMap(selectWhere));
				// 符合时间条件的销售单
				selectWhere.put("createDateStart", map.get("sellCreateDateStart"));
				selectWhere.put("createDateEnd", map.get("sellCreateDateEnd"));
				invoicingInfo.setSellOrders(sellOrderMapper.selectSellOrderByMap(selectWhere));
				selectWhere.remove("sortField");
				// 符合时间条件的损耗
				selectWhere.put("createDateStart", map.get("lossCreateDateStart"));
				selectWhere.put("createDateEnd", map.get("lossCreateDateEnd"));
				invoicingInfo.setLosses(lossMapper.selectLossByMap(selectWhere));
				list.add(invoicingInfo);
			}
		} finally {
			session.close();
		}
		return list;
	}

	// @SuppressWarnings("unchecked")
	// public static void main(String[] args) throws Exception {
	// List<InvoicingInfo> invoicingInfos = new GoodsService().selectInvoicingInfo(new HashMap<String, Object>());
	// for (InvoicingInfo invoicingInfo : invoicingInfos) {
	// Map<BuyOrder, Object[]> map = invoicingInfo.getBuyOrderBindOfSL();
	// for (BuyOrder key : map.keySet()) {
	// Object[] obj = map.get(key);
	// List<SellOrderGoods> sellOrderGoods = (List<SellOrderGoods>) obj[0];
	// List<Loss> losses = (List<Loss>) obj[1];
	// System.out.print(invoicingInfo.getGoods().getName() + "\t" + key.getId() + "\t");
	// double bnumber = 0, bprice = 0;
	// double snumber = 0, sprice = 0;
	// double lnumber = 0, lprice = 0;
	// for (OrderGoods buyOrderGoods : key.getOrderGoods()) {
	// if (buyOrderGoods.getGoodsId() == invoicingInfo.getGoods().getId()) {
	// bnumber += buyOrderGoods.getNumber();
	// bprice += buyOrderGoods.getNumber() * buyOrderGoods.getPrice();
	// }
	// }
	// for (SellOrderGoods orderGoods : sellOrderGoods) {
	// if (orderGoods.getGoodsId() == invoicingInfo.getGoods().getId()) {
	// snumber += orderGoods.getNumber();
	// sprice += orderGoods.getNumber() * orderGoods.getPrice();
	// }
	// }
	// for (Loss loss : losses) {
	// lnumber += loss.getNumber();
	// lprice += loss.getOrderGoods().getPrice()* loss.getNumber();
	// }
	// System.out.print(NumberUtil.convert(bnumber) + "\t");
	// System.out.print(NumberUtil.convert(bprice) + "\t");
	// System.out.print(NumberUtil.convert(snumber) + "\t");
	// System.out.print(NumberUtil.convert(sprice) + "\t");
	// System.out.print(NumberUtil.convert(lnumber) + "\t");
	// System.out.print(NumberUtil.convert(lprice) + "\t");
	// System.out.println();
	// }
	// System.out.println();
	// }
	// }

	public void updateGoodsNumber(List<Goods> goodsList) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
			for (Goods goods : goodsList) {
				goodsMapper.updateGoodsNumber(goods);
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

}
