package cn.hurry.util;

import java.util.Map;
import java.util.Map.Entry;

/**
 * 分页参数生成类
 * 
 * @author 共有
 * 
 */
public class URLParameterUtil {

	public static String createURLParameter(Map<String, Object> urlParameterMap) {
		StringBuilder parameterSb = new StringBuilder();
		for (Entry<String, Object> iterable_element : urlParameterMap.entrySet()) {
			parameterSb.append("&" + iterable_element.getKey());
			parameterSb.append("=" + iterable_element.getValue());
		}
		return parameterSb.toString();
	}
}
