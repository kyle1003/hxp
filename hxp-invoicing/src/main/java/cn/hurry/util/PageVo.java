/*
 * 分页的VO
 */
package cn.hurry.util;

import java.util.List;

/**
 * 分页公共类（框架） 已放弃使用
 */
@Deprecated
public class PageVo {

	/**
	 * 对象集合
	 */
	private List<?> pageList;

	/**
	 * 对象总个数
	 */
	private int totalNum;

	public List<?> getPageList() {
		return pageList;
	}

	public void setPageList(List<?> pageList) {
		this.pageList = pageList;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
}
