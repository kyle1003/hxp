//package cn.hurry.service.print;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.ibatis.session.SqlSession;
//import org.springframework.stereotype.Service;
//
//import com.google.gson.Gson;
//
//import cn.hurry.data.SessionFactory;
//import cn.hurry.data.mapper.order.sell.SellOrderMapper;
//import cn.hurry.data.mapper.succession.SuccessionMapper;
//import cn.hurry.po.order.Order;
//import cn.hurry.po.order.OrderGoods;
//import cn.hurry.po.order.sell.SellOrder;
//import cn.hurry.po.succession.Succession;
//import cn.hurry.print.PrintNetwork;
//import cn.hurry.print.formater.DataFormater;
//import cn.hurry.print.formater.TableFormater;
//import cn.hurry.util.DateTimeUtils;
//import cn.hurry.util.NumberUtil;
//import cn.hurry.util.PrintDataBase64;
//import cn.hurry.util.PropertiesUtil;
//
//@Service
//public class PrintService {
//	public static String title = "小票";
//	public static String tel = "联系电话:18502314099";
//	public static String add = "地址：四川成都";
//
//
//	public static void main(String[] args) {
//		try {
//			DataFormater dataFormater = new PrintService().createSellPrintData("XS20140107000010", "10", "40", "50");
//			// DataFormater dataFormater = new PrintService().createWorkOverSellInfoPrintData(1, true);
//			new PrintNetwork(dataFormater).print("127.0.0.1", 5336);
//			// System.out.println(new String(PropertiesUtil.getValueByKey("server", "title").getBytes("ISO-8859-1"), "UTF-8"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public String encodeBase64Data(DataFormater dataFormater) {
//		return PrintDataBase64.getEncryptedString(PrintDataBase64.ALL_KEY, new Gson().toJson(dataFormater)).replace("\r\n", "");
//	}
//
//	public DataFormater createWorkOverSellInfoPrintData(int id, boolean all) throws Exception {
//		SqlSession session = SessionFactory.openSession();
//		try {
//			SuccessionMapper successionMapper = session.getMapper(SuccessionMapper.class);
//			HashMap<String, Object> where = new HashMap<String, Object>();
//			Succession succession = successionMapper.selectSuccessionById(id);
//			where.put("successionInfoId", succession.getSuccessionInfoId());
//
//			// 制作表头
//			Map<String, Object> map = new LinkedHashMap<String, Object>();
//			map.put(TableFormater.TABLE_FIELD_STRING, "id");
//			map.put(TableFormater.TABLE_HEADER_TITLE_STRING, "流水号");
//			map.put(TableFormater.TABLE_MARGIN_LEFT_STRING, 50);
//
//			Map<String, Object> map2 = new LinkedHashMap<String, Object>();
//			map2.put(TableFormater.TABLE_FIELD_STRING, "pay");
//			map2.put(TableFormater.TABLE_HEADER_TITLE_STRING, "小计");
//			map2.put(TableFormater.TABLE_MARGIN_LEFT_STRING, 90);
//
//			List<Map<String, Object>> headers = new ArrayList<Map<String, Object>>();
//			headers.add(map);
//			headers.add(map2);
//			String[] topString = new String[] { (!all ? "结班单据" : "日结单据"),
//					"操作员:" + succession.getTakeOverUser().getUsername() + " 时间:" + DateTimeUtils.format(new Date(), DateTimeUtils.YEAR_MONTH_DAY) };
//
//			List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
//			double countPay = 0;
//			double countPrice = 0;
//			if (all) {
//				List<Succession> successions = successionMapper.selectSuccessionByMap(where);
//				for (Succession succession2 : successions) {
//					for (Order order : succession2.getSellOrderList()) {
//						Map<String, String> map3 = new LinkedHashMap<String, String>();
//						for (OrderGoods goods : order.getOrderGoods()) {
//							countPrice += goods.getPrice() * goods.getNumber();
//						}
//						map3.put("id", order.getId());
//						map3.put("pay", order.getPay() + "");
//						datas.add(map3);
//						countPay += order.getPay();
//					}
//				}
//			} else {
//				for (Order order : succession.getSellOrderList()) {
//					Map<String, String> map3 = new LinkedHashMap<String, String>();
//					for (OrderGoods goods : order.getOrderGoods()) {
//						countPrice += goods.getPrice() * goods.getNumber();
//					}
//					map3.put("id", order.getId());
//					map3.put("pay", order.getPay() + "");
//					datas.add(map3);
//					countPay += order.getPay();
//				}
//			}
//			String[] footString = new String[] { "合计金额:" + NumberUtil.convert(countPrice) + " 实付金额:" + NumberUtil.convert(countPay), "挂 帐: ____  其 他:____" };
//			TableFormater formater = new TableFormater(headers, datas);
//			formater.setTopString(topString);
//			formater.setFootString(footString);
//			formater.setNewLineMaxSize(20);
//			return formater;
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			session.close();
//		}
//	}
//
//	public DataFormater createSellPrintData(String id, String zl, String sdp, String sf) throws Exception {
//		title =PropertiesUtil.getValueByKey("server", "title");
//		tel =PropertiesUtil.getValueByKey("server", "tel");
//		add =PropertiesUtil.getValueByKey("server", "add");
//		SqlSession session = SessionFactory.openSession();
//		try {
//			SellOrderMapper mapper = session.getMapper(SellOrderMapper.class);
//			SellOrder order = mapper.selectSellOrderById(id);
//			// 制作表头
//			Map<String, Object> map = new LinkedHashMap<String, Object>();
//			map.put(TableFormater.TABLE_FIELD_STRING, "name");
//			map.put(TableFormater.TABLE_HEADER_TITLE_STRING, "名称");
//			map.put(TableFormater.TABLE_MARGIN_LEFT_STRING, 40);
//
//			Map<String, Object> map2 = new LinkedHashMap<String, Object>();
//			map2.put(TableFormater.TABLE_FIELD_STRING, "number");
//			map2.put(TableFormater.TABLE_HEADER_TITLE_STRING, "数量");
//			map2.put(TableFormater.TABLE_MARGIN_LEFT_STRING, 60);
//
//			Map<String, Object> map3 = new LinkedHashMap<String, Object>();
//			map3.put(TableFormater.TABLE_FIELD_STRING, "price");
//			map3.put(TableFormater.TABLE_HEADER_TITLE_STRING, "价格");
//			map3.put(TableFormater.TABLE_MARGIN_LEFT_STRING, 50);
//			List<Map<String, Object>> headers = new ArrayList<Map<String, Object>>();
//			headers.add(map);
//			headers.add(map2);
//			headers.add(map3);
//			String[] topString = new String[] { title, "操作员:" + order.getUser().getUsername() + "时间:" + DateTimeUtils.format(new Date(), DateTimeUtils.YEAR_MONTH_DAY), " 流水号:" + order.getId() };
//			String[] footString = new String[] { "应付:" + sdp + " 实付:" + sf, "找零:" + zl, add, tel };
//
//			List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
//			for (OrderGoods goods : order.getOrderGoods()) {
//				Map<String, String> data_ = new LinkedHashMap<String, String>();
//				data_.put("name", goods.getGoods().getName());
//				data_.put("number", goods.getNumber() + "");
//				data_.put("price", goods.getPrice() + "");
//				datas.add(data_);
//			}
//			TableFormater formater = new TableFormater(headers, datas);
//			formater.setTopString(topString);
//			formater.setFootString(footString);
//			return formater;
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			session.close();
//		}
//	}
//}
