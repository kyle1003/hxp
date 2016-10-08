package cn.hurry.manage.succession;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;

import cn.hurry.manage.Cache;
import cn.hurry.po.succession.SuccessionInfo;
import cn.hurry.service.succession.SuccessionInfoService;
import cn.hurry.util.DateTimeUtils;
import cn.hurry.util.StringUtil;

public class SuccessionInfoManage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8162270557388874886L;

	static {
		init();
	}

	public static void init() {
		SuccessionInfoService successionInfoService = new SuccessionInfoService();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("statusNot", SuccessionInfo.status_isSettle);

			List<SuccessionInfo> successionInfos = successionInfoService.selectSuccessionInfoByMap(map);
			for (SuccessionInfo successionInfo : successionInfos) {
				put(successionInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SuccessionInfo getSuccessionInfoById(int id) {
		return Cache.get(SuccessionInfo.class, id);
	}

	public static List<SuccessionInfo> getSuccessionInfoList() {
		return Cache.get(SuccessionInfo.class);
	}

	public static void clear() {
		Cache.clear(SuccessionInfo.class);
	}

	public static void remove(int id) {
		Cache.remove(SuccessionInfo.class, id);
	}

	public static void put(SuccessionInfo successionInfo) {
		if (successionInfo == null)
			return;
		Cache.put(successionInfo.getId(), successionInfo);
	}

	public static SuccessionInfo getSuccessionInfoByStatus(Byte... status) {
		List<SuccessionInfo> list = Cache.get(SuccessionInfo.class);
		if (list == null)
			return null;
		for (SuccessionInfo successionInfo : list) {
			if (StringUtil.containObjInArr(status, successionInfo.getStatus())) {
				return successionInfo;
			}
		}
		return null;
	}

	public static SuccessionInfo getSuccessionInfoByDate(Date date) {
		List<SuccessionInfo> list = Cache.get(SuccessionInfo.class);
		if (list == null) {
			init();
			list = Cache.get(SuccessionInfo.class);
			if (list == null) {
				return null;
			}
		}
		for (SuccessionInfo successionInfo : list) {
			if (DateTimeUtils.compareDaysWithDay(successionInfo.getDate(), date) == 0) {
				return successionInfo;
			}
		}
		return null;
	}

	public static SuccessionInfo getToDaySuccessionInfo() {
		Date date = new Date();
		SuccessionInfo successionInfo = getSuccessionInfoByStatus(SuccessionInfo.status_settleing, SuccessionInfo.status_notStart,
				SuccessionInfo.status_working);
		if (successionInfo == null) {
			try {
				// 获取今天日结信息
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("date", DateTimeUtils.format(new Date(), DateTimeUtils.YEAR_MONTH_DAY + " 00:00:00"));
				SuccessionInfoService successionInfoService = new SuccessionInfoService();
				List<SuccessionInfo> successionInfos = successionInfoService.selectSuccessionInfoByMap(map);
				//今日已有日结信息（今日的日结信息已结算）
				if (successionInfos != null && successionInfos.size() > 0) {
					return successionInfos.get(0);
				}
				successionInfo = new SuccessionInfo();
				successionInfo.setDate(new DateTime(date).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toDate());
				successionInfo.setStatus(SuccessionInfo.status_notStart);
				return successionInfoService.insertSuccessionInfo(successionInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return successionInfo;
	}

}
