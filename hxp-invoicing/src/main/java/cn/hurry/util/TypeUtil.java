package cn.hurry.util;

/**
 * 通用类型工具
 * 
 * @author zh.sqy@qq.com
 * 
 */
public class TypeUtil {

	/**
	 * 性别：女
	 */
	public static final byte SEX_FEMALE = 0;

	/**
	 * 性别：男
	 */
	public static final byte SEX_MALE = 1;

	/**
	 * 根据性别获取性别名称
	 * 
	 * @param sex
	 *            性别
	 * @return 性别名称
	 */
	public static String getSexName(byte sex) {
		return sex == 0 ? "女" : "男";
	}

}
