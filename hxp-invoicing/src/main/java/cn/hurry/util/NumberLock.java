package cn.hurry.util;

import java.util.HashMap;

/**
 * 数字锁
 * 
 * @author zh.sqy@qq.com
 * 
 */
public class NumberLock {

	/**
	 * 相同数字重复操作锁
	 */
	private static final HashMap<Integer, Integer> NUMBER_LOCK = new HashMap<Integer, Integer>();

	/**
	 * 相同数字重复操作锁次数统计
	 */
	private static final HashMap<Integer, Integer> NUMBER_LOCK_COUNT = new HashMap<Integer, Integer>();

	/**
	 * 获取数字锁
	 * 
	 * @param number
	 *            数字
	 * 
	 * @return 数字锁
	 */
	public static Integer getNumberLock(int number) {
		synchronized (NUMBER_LOCK) {
			Integer lock = NUMBER_LOCK.get(number);
			if (lock == null) {
				lock = number;
				NUMBER_LOCK.put(number, lock);
				NUMBER_LOCK_COUNT.put(number, 1);
			} else {
				int count = NUMBER_LOCK_COUNT.get(number);
				NUMBER_LOCK_COUNT.put(number, count + 1);
			}

			return lock;
		}
	}

	/**
	 * 释放数字锁
	 * 
	 * @param number
	 *            数字
	 */
	public static void releaseNumberLock(int number) {
		synchronized (NUMBER_LOCK) {
			int count = NUMBER_LOCK_COUNT.get(number);
			if (count - 1 <= 0) {
				NUMBER_LOCK.remove(number);
				NUMBER_LOCK_COUNT.remove(number);
			} else {
				NUMBER_LOCK_COUNT.put(number, count - 1);
			}
		}
	}
}
