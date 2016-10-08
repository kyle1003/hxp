package cn.hurry.util;

import java.util.Date;
import java.util.Random;

/**
 * 常用工具类
 * 
 * @author zh.sqy@qq.com
 * 
 * @version $Revision: 297 $ <br>
 *          $LastChangedBy: zh.sqy@qq.com $ <br>
 *          $LastChangedDate: 2012-03-28 21:02:24 +0800 (周三, 2012-03-28) $
 */
public final class CommonUtils {

	/**
	 * 随机对象
	 */
	private static final Random RANDOM = new Random();

	/**
	 * 锁对象
	 */
	private static final Object LOCK = new Object();

	/**
	 * 唯一标识
	 */
	private static long uniqueId = System.currentTimeMillis();

	/**
	 * 获取唯一标识
	 * 
	 * @return 唯一标识
	 */
	public static long getUniqueId() {
		synchronized (LOCK) {
			return ++uniqueId;
		}
	}

	/**
	 * 获取int类型随机数
	 * 
	 * @param maxInt
	 *            最大int值
	 * @return int类型随机数
	 */
	public static int getRandomInt(int maxInt) {
		return RANDOM.nextInt(maxInt);
	}

	/**
	 * 获取long类型随机数
	 * 
	 * @return long类型随机数
	 */
	public static long getRandomLong() {
		return RANDOM.nextLong();
	}

	/**
	 * 获取指定长度的int类型随机数
	 * 
	 * @param length
	 *            长度（1-9）
	 * 
	 * @return 指定长度的int类型随机数
	 */
	public static int getFixLengthRandomInt(int length) {
		StringBuilder sb = new StringBuilder(length);
		sb.append("1");
		for (int i = 0; i < length; i++) {
			sb.append("0");
		}
		int maxInt = Integer.parseInt(sb.toString());
		int minInt = Integer.parseInt(sb.substring(0, sb.length() - 1));
		int randomInt = getRandomInt(maxInt);
		if (randomInt < minInt) {
			randomInt += minInt;
		}
		return randomInt;
	}

	/**
	 * 获取固定范围int类型随机数
	 * 
	 * @param minValue
	 *            最小值
	 * @param maxValue
	 *            最大值
	 * 
	 * @return 固定范围int类型随机数
	 */
	public static int getFixRangeRandomInt(int minValue, int maxValue) {
		return getRandomInt(maxValue - minValue + 1) + minValue;
	}

	/**
	 * 指定时间是否过期
	 * 
	 * @param date
	 *            时间
	 * 
	 * @return 是否过期
	 */
	public static boolean isExpiry(Date date) {
		if (date == null) {
			return true;
		}
		return new Date().after(date);
	}
	
	/**
	 * 比较一个时间是否在另外一个时间之后
	 * 
	 * @param date
	 *            时间
	 * @param afterdate 
	 * @return 是否过期
	 */
	public static boolean isExpiry(Date date,Date afterdate) {
		if (date == null) {
			return true;
		}
		return date.after(afterdate);
	}

	/**
	 * 在指定的毫秒数内让当前正在执行的线程休眠（暂停执行）
	 * 
	 * @param millis
	 *            以毫秒为单位的休眠时间
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
