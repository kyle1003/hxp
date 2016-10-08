package cn.hurry.util;

import java.util.HashMap;
import java.util.List;

import jxl.write.Label;
import jxl.write.WritableSheet;

public class ExcelUtil {

	/**
	 * 传入要组建的表数据（这里是单个表格WritableSheet对象，不进行整表的创建）
	 * 
	 * @param sheet
	 *            要组件的单个表格对象
	 * @param headers
	 *            表头（就是数据的第一行） 有2种数据 1 header 代表表头（第一行）的内容,field 代表这一列数据的字段（标识） 与dataList中的key相对应
	 * @param dataList
	 *            表数据 map中的key 与headers集合中field对应
	 * @return
	 * @throws Exception
	 */
	public static WritableSheet encodeSheet(WritableSheet sheet, List<HashMap<?, ?>> headers, List<HashMap<?, ?>> dataList) throws Exception {
		int columnsize = 10;
		for (int i = 0; i < headers.size(); i++) {
			HashMap<?, ?> hashMap = headers.get(i);
			// 表头
			String header = hashMap.get("header") != null ? hashMap.get("header").toString() : "";
			// 字段标识
			String field = hashMap.get("field") != null ? hashMap.get("field").toString() : "";
			header = replaceString(header);// 替换非法字符
			sheet.addCell(new Label(i, 1, header));// 添加表头
			// 添加表头对应的数据
			for (int j = 0; j < dataList.size(); j++) {
				// sheet.addCell(new Label(i, j*2+1, header)); //每行数据对应一个表头
				HashMap<?, ?> map = dataList.get(j);
				String value = map.get(field) + "";
				sheet.addCell(new Label(i, j + 2, value));
				if (value.length() + 7 > columnsize) {
					columnsize = value.length() + 7;
				}
				// sheet.addCell(new Label(i, j * 2+2, value));//每行数据对应一个表头
			}
			sheet.setColumnView(i, columnsize);// 自动增加宽度 
		}
		//合并第一行
		sheet.mergeCells(0, 0, headers.size() - 1, 0);
		sheet.setRowView(0, 500);
		return sheet;
	}

	public static String replaceString(String str) {
		return str.replace("\\n", "").replace("\\t", "").replace(" ", "");
	}
}
