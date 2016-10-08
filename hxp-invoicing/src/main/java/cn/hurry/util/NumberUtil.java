package cn.hurry.util;

public class NumberUtil {
	/**
	 * 精确到小数点后3位
	 * 
	 * @param value
	 * @return
	 */
	public static double convert(double value) {
		long lg = Math.round(value * 1000); // 四舍五入
		double d = lg / 1000.0; // 注意：使用 1000.0而不是 1000
		return d;
	}

}
