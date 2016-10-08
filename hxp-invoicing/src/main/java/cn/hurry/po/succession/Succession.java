package cn.hurry.po.succession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hurry.manage.Cache;
import cn.hurry.po.order.Order;
import cn.hurry.po.order.sell.SellOrder;
import cn.hurry.po.user.User;
import cn.hurry.service.order.OrderService;
import cn.hurry.service.user.UserService;
import cn.hurry.util.DateTimeUtils;
import cn.hurry.util.NumberUtil;

/**
 * 交接班，以链表形式进行
 * 
 * @author ZhouHao
 * 
 */
public class Succession implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -303159945185596111L;

	/**
	 * 交接班状态：正在上班
	 */
	public static final byte STATUS_WORKING = 0;

	/**
	 * 交接班状态：已交班
	 */
	public static final byte STATUS_WORKOVER = 1;

	/**
	 * 交接班状态：已交班未接班
	 */
	public static final byte STATUS_WORKOVER_BUT_NOT_HANDOVER = 2;

	private int id;

	/**
	 * 接班人编号：如果为0表示为最后一班
	 */
	private int takeOverUserId;

	/**
	 * 接班人
	 */
	private User takeOverUser;

	/**
	 * 交班id，为0表示当天第一班
	 */
	private int handOverSuccessionId;

	/**
	 * 交班信息
	 */
	private Succession handOverSuccession;

	/**
	 * 本次上班期间的销售情况
	 */
	private String sellOrderIds;

	/**
	 * 接班时间
	 */
	private Date takeOverTime;

	/**
	 * 结班时间
	 */
	private Date handOverTime;

	/**
	 * 状态
	 */
	private byte status;

	/**
	 * 销售单集合
	 */
	private List<SellOrder> sellOrderList = new ArrayList<SellOrder>();

	/**
	 * 日结信息编号
	 */
	private int successionInfoId;

	/**
	 * 日结信息
	 */
	private SuccessionInfo successionInfo;

	private String remark;
	
	public int getSuccessionInfoId() {
		return successionInfoId;
	}

	public void setSuccessionInfoId(int successionInfoId) {
		this.successionInfoId = successionInfoId;
	}

	public SuccessionInfo getSuccessionInfo() {
		return successionInfo;
	}

	public void setSuccessionInfo(SuccessionInfo successionInfo) {
		this.successionInfo = successionInfo;
	}

	public User getTakeOverUser() {
		if (takeOverUser == null) {
			takeOverUser = Cache.get(User.class, takeOverUserId);
			if (takeOverUser == null) {
				try {
					takeOverUser = new UserService().getUserById(takeOverUserId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return takeOverUser;
	}

	public void setTakeOverUser(User takeOverUser) {
		this.takeOverUser = takeOverUser;
	}

	public int getHandOverSuccessionId() {
		return handOverSuccessionId;
	}

	public void setHandOverSuccessionId(int handOverSuccessionId) {
		this.handOverSuccessionId = handOverSuccessionId;
	}

	public Succession getHandOverSuccession() {
		return handOverSuccession;
	}

	public void setHandOverSuccession(Succession handOverSuccession) {
		this.handOverSuccession = handOverSuccession;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTakeOverUserId() {
		return takeOverUserId;
	}

	public void setTakeOverUserId(int takeOverUserId) {
		this.takeOverUserId = takeOverUserId;
	}

	public String getSellOrderIds() {
		return sellOrderIds;
	}

	public void setSellOrderIds(String sellOrderIds) {
		this.sellOrderIds = this.sellOrderIds == null ? "" + sellOrderIds : this.sellOrderIds + "," + sellOrderIds;
	}

	public Date getTakeOverTime() {
		return takeOverTime;
	}

	public void setTakeOverTime(Date takeOverTime) {
		this.takeOverTime = takeOverTime;
	}

	public List<SellOrder> getSellOrderList() {
		String slods[] = null;
		// 是否存在销售单编号
		if (sellOrderIds != null) {
			slods = sellOrderIds.split(",");
		}
		if (slods != null) {
			// 遍历编号
			for (String id : slods) {
				// 获取缓存里面的数据
				SellOrder sellOrder = Cache.get(SellOrder.class, id);
				// 缓存里没有数据
				if (sellOrder == null) {
					try {
						// 获取数据库数据
						sellOrder = (SellOrder) new OrderService().selectOrderById(id);
						// 加载缓存
						if (sellOrder != null)
							Cache.put(sellOrder.getId(), sellOrder);
					} catch (Exception e) {
						e.printStackTrace();
						sellOrder = null;
					}
				}
				// 添加到销售单集合
				if (sellOrder != null) {
					sellOrderList.add(sellOrder);
				}
			}
		}
		return this.sellOrderList;
	}

	public void setSellOrderList(List<SellOrder> sellOrderList) {
		this.sellOrderList = sellOrderList;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Date getHandOverTime() {
		return handOverTime;
	}

	public void setHandOverTime(Date handOverTime) {
		this.handOverTime = handOverTime;
	}

	public double getPay() {
		double p = 0;
		for (Order order : getSellOrderList()) {
			p += order.getPay();
		}
		return NumberUtil.convert(p);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("id=" + id);
		stringBuilder.append(",username=" + getTakeOverUser().getUsername());
		stringBuilder.append(",sellOrderIdsLength=" + (sellOrderIds == null ? 0 : sellOrderIds.split(",").length));
		stringBuilder.append(",handOverTime=" + (getHandOverTime()==null?"-":DateTimeUtils.format(getHandOverTime(), DateTimeUtils.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)));
		stringBuilder.append(",takeOverTime=" + (getTakeOverTime()==null?"-":DateTimeUtils.format(getTakeOverTime(), DateTimeUtils.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)));
		stringBuilder.append(",status_cn="
				+ (getStatus() == STATUS_WORKOVER ? "已结班" : getStatus() == STATUS_WORKOVER_BUT_NOT_HANDOVER ? "已结班，等待下一班"
						: getStatus() == STATUS_WORKING ? "收银中" : "-"));
		stringBuilder.append(",pay=" + getPay());
		stringBuilder.append(",remark=" + getRemark());
		stringBuilder.append(",takeOverTime=" + (DateTimeUtils.format(getTakeOverTime(), DateTimeUtils.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)));
		return stringBuilder.toString();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
