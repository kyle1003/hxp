package cn.hurry.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapUtil {
	/**
	 * 对一个对象集合进行转换 将字符串以键值对形式存入map后重新装入集合。对象需要重写toString方法。格式为key=value,key=value
	 * 
	 * @param 要进行转换的字符串集合
	 * @return 转换后的集合
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<?> fomatData(List<?> list) throws Exception {
		ArrayList data = new ArrayList();
		for (int i = 0, l = list.size(); i < l; i++) {
			// 将单个数据转换为hashmap
			HashMap record =null;
			try{
				record = StringUtil.string2HashMap(list.get(i).toString());
			}catch (Exception e) {
			}
			if (record == null) {
				continue;
			}
			data.add(record);
		}
		return data;
	}
	
}
