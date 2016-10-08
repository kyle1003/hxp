package cn.hurry.po.succession;

import java.io.Serializable;
import java.util.Date;

import cn.hurry.util.DateTimeUtils;

/**
 * 交接班日结信息
 * 
 * @author ZhouHao
 * 
 */
public class SuccessionInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7479500601666128274L;

	/**
	 * 状态：未开班
	 */
	public static final byte status_notStart = 0;

	/**
	 * 状态：上班中
	 */
	public static final byte status_working = 1;

	/**
	 * 状态：交班中
	 */
	public static final byte status_settleing = 3;

	/**
	 * 状态：已日结
	 */
	public static final byte status_isSettle = 2;

	/**
	 * 编号
	 */
	private int id;

	/**
	 * 日期：精确到天
	 */
	private Date date;

	/**
	 * 状态
	 */
	private byte status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("id=" + id);
		builder.append(",date=" + DateTimeUtils.format(getDate(), DateTimeUtils.YEAR_MONTH_DAY));
		builder.append(",status_cn="
				+ (status == status_isSettle ? "已日结" : status == status_notStart ? "未开班" : status == status_settleing ? "轮班中"
						: status == status_working ? "收银中" : "-"));
		return builder.toString();
	}
}
