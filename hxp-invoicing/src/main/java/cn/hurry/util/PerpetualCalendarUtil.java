package cn.hurry.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

/**
 * 万年历工具
 * 
 * @author ZhouHao
 * 
 */
public class PerpetualCalendarUtil {
	public static List<Lunar> getLunarByMonth(Date date) {
		List<Lunar> lunars = new ArrayList<Lunar>();
		int maxMonth = DateTimeUtils.getDateOfMonth(date);
		for (int i = 1; i <= maxMonth; i++) {
			DateTime dateTime = new DateTime(date).withDayOfMonth(i);
			lunars.add(DateTimeUtils.getLunar(dateTime.toDate()));
		}
		return lunars;
	}
}
