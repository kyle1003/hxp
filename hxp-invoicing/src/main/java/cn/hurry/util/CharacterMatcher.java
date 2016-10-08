package cn.hurry.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串验证类 用于验证字符串是否匹配正则表达式
 * 
 * @author zh.sqy@qq.com
 * 
 */
public class CharacterMatcher {

	/**
	 * 验证字符串是否匹配数字(至少1位数字)
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean matcherNum(String str) {

		Pattern patt = Pattern.compile("^\\d{1,}$");

		Matcher matcher = patt.matcher(str);

		return matcher.matches();
	}

	/**
	 * 验证字符串是否是整数（包括正整数，负整数）
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean matcherInteger(String str) {

		Pattern patt = Pattern.compile("^-?\\d+$");
		Matcher matcher = patt.matcher(str);

		return matcher.matches();
	}

	/**
	 * 字符串是否匹配数字和字母的组合
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean matcherStrAndNum(String str) {

		Pattern patt = Pattern.compile("^[A-Za-z0-9]+$");

		Matcher matcher = patt.matcher(str);

		return matcher.matches();
	}

	/**
	 * 字符串是否匹配数字或者字母
	 * 
	 * @param str
	 *            单个字符的字符串
	 * @return
	 */
	public static boolean matcherLettersAndNum(String str) {

		Pattern patt = Pattern.compile("[A-Za-z0-9]");

		Matcher matcher = patt.matcher(str);

		return matcher.matches();
	}

	/**
	 * 字符串匹配日期格式：yyyy-mm-dd
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean matcherDate(String str) {

		Pattern patt = Pattern
				.compile("^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$");

		Matcher matcher = patt.matcher(str);

		return matcher.matches();
	}

	/**
	 * 字符串匹配时间格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean matcherDateTime(String str) {

		Pattern patt = Pattern
				.compile("^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))\\s(20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$");

		Matcher matcher = patt.matcher(str);

		return matcher.matches();
	}

}
