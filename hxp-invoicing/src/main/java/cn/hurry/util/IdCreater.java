package cn.hurry.util;

import java.util.Date;

import org.joda.time.DateTime;

import cn.hurry.po.order.Order;

/**
 * Id生成器
 * 
 * @author ZhouHao
 * 
 */
public class IdCreater {

	private static final IdCreater idCreater = new IdCreater();

	private IdCreater() {
	}

	public static IdCreater getInstance() {
		return idCreater;
	}

	private boolean isLock = false;

	public static void main(String[] args) {
		String maxId = "XS20140108000001";
		System.out.println("key=" + Order.ID_KEY_SEL);
		System.out.println("maxId=" + maxId);
		maxId = IdCreater.getInstance().createId(Order.ID_KEY_SEL, maxId);
		IdCreater.getInstance().unlock();
		System.out.println("nextId=" + maxId);
		System.out.println("nextId=" + IdCreater.getInstance().createId(Order.ID_KEY_SEL, maxId));
		new Thread() {
			public void run() {
				String maxId2 = "XS20140108000001";
				System.out.println("maxId2=" + maxId2);
				System.out.println("nextId2=" + IdCreater.getInstance().createId(Order.ID_KEY_SEL, null));
			};
		}.start();
		try {
			Thread.sleep(1000);
			IdCreater.getInstance().unlock();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取ID创建时间
	 * 
	 * @param key
	 *            ID生成规则
	 * @param id
	 *            ID
	 * @return 创建时间
	 */
	private Date getIdCreateDate(String key, String id) {
		int beginIndex = key.indexOf("yyyyMMdd");
		int endIndex = key.indexOf("{");
		String dateStr = id.substring(beginIndex, endIndex);
		return DateTimeUtils.formatDateString(dateStr, "yyyyMMdd");
	}

	/**
	 * 获取id序列
	 * 
	 * @param key
	 *            id生成规则
	 * @param id
	 *            id
	 * @return id序列
	 */
	public int getIdIndex(String key, String id) {
		String index = id.substring(key.indexOf("{"));
		return Integer.parseInt(index);
	}

	/**
	 * 在创建完ID后必须使用次方法进行解锁
	 */
	public void unlock() {
		synchronized (idCreater) {
			idCreater.notify();
			isLock = false;
		}
	};

	/**
	 * 生成ID ,在ID被使用生效后（添加到数据库）需要手动解锁,否则无法再次使用
	 * 
	 * @param key
	 *            生成ID的KEY 格式为**yyyyMMdd{n} **为任意2个字符 n为后面数字的位数
	 * @param maxNumber
	 *            当前数据库中ID最大值
	 * @return 数据库中ID最大值的下一个ID 如KEY=FHyyyyMMdd{5} maxNumber=FH2013081210025 将返回FH2013081210026
	 */
	public String createId(String key, String max) {
		synchronized (idCreater) {
			if (isLock) {
				try {
					// 已锁定，等待
					idCreater.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			String maxId = "0";
			if (max != null) {
				maxId = max.substring(key.indexOf("}"));
				DateTime maxDate = new DateTime(getIdCreateDate(key, max));
				DateTime dateTime = new DateTime(new Date());
				if (dateTime.getYear() != maxDate.getYear() || dateTime.getMonthOfYear() != maxDate.getMonthOfYear()
						|| dateTime.getDayOfMonth() != maxDate.getDayOfMonth()) {
					maxId = "0";
				}
			}
			// 替换key中格式时间
			String timeKey = key.replace("yyyyMMdd", DateTimeUtils.format(new Date(), "yyyyMMdd")).substring(0, key.indexOf("{"));
			// 创建数字序列
			String numberKey = key.substring(key.indexOf("{"), key.indexOf("}") + 1);
			String numberLenth = numberKey.replace("{", "").replace("}", "");
			String id = timeKey + "" + getNumber(isJw(Integer.parseInt(maxId)) ? Integer.parseInt(numberLenth) - 1 : Integer.parseInt(numberLenth), Integer.parseInt(maxId));
			isLock = true;
			return id;
		}
	}

	/**
	 * 判断数字是否到进位数 如9，99，999....
	 * 
	 * @param number
	 * @return 是否到进位数
	 */
	private boolean isJw(Object number) {
		return ("" + number).matches("[.9]|[.9]+[.9]");
	}

	/**
	 * 根据位数和最大数 获取比最大数大1的数字
	 * 
	 * @param size
	 *            数字位数
	 * @param maxNumber
	 *            最大数
	 * @return 如位数为5 最大数为1 返回 00002
	 */
	public synchronized String getNumber(int size, long maxNumber) {
		String nstr = "";
		String maxNumberStr = maxNumber + "";
		for (int i = 0; i < size; i++) {
			nstr += "0";
		}
		nstr = nstr.substring(0, nstr.length() - maxNumberStr.length()) + (maxNumber + 1);
		return nstr;
	}

}
