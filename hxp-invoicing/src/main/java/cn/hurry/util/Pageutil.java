package cn.hurry.util;

import java.util.HashMap;
import java.util.Map;

import cn.hurry.json.JsonDecoder;
import cn.hurry.json.util.MapUtils;

/**
 * 分页 工具
 * 
 * @author ZhouHao
 * 
 */
public class Pageutil {
	/**
	 * 第几页 从0开始
	 */
	private int pageIndex;

	/**
	 * 每页显示记录条数
	 */
	private int pageSize;

	/**
	 * 排序字段
	 */
	private String sortField;

	/**
	 * 排序方式 DESC 反序 ASC 正序
	 */
	private String sortOrder;

	/**
	 * 搜索关键字 格式为key=value,key2=value2
	 */
	private String key;

	private Map<String, Object> queryMap;

	public Map<String, Object> getQueryMap() {
		if (queryMap == null)
			try {
				queryMap = JsonDecoder.getInstance(Map.class).decodeMap(getKey());
			} catch (Exception e) {
				queryMap = new HashMap<String, Object>();
			}
		return queryMap = MapUtils.removeNullValue(queryMap);
	}

	public int getStart() {
		return pageIndex * pageSize;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

}
