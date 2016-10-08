package cn.hurry.util;

import java.util.Date;

import org.joda.time.DateTime;

/**
 * 授权工具类
 * 
 * @author zh.sqy@qq.com
 * 
 */
public class AuthorizeUtil {

	/**
	 * 获取部分授权码
	 * 
	 * @param authorizeCode
	 *            授权码
	 * @return 部分授权码
	 * @throws Exception
	 */
	public static String getPartAuthorizeCode(String authorizeCode) throws Exception {
		return Base64.getDecryptedString(Base64.ALL_KEY, authorizeCode);
	}

	/**
	 * 是否是本机授权码
	 * 
	 * @param partAuthorizeCode
	 *            部分授权码
	 * @return 是否是本机授权码
	 */
	public static boolean isLocalAuthorizeCode(String partAuthorizeCode) {
		boolean isLocalAuthorizeCode = false;
		if (partAuthorizeCode.length() >= 16) {
			String macCode = partAuthorizeCode.substring(0, 16);
			String realCode = AuthorizeMD5.getMD5By16(MacUtil.getMACAddress());
			if (macCode.equals(realCode)) {
				isLocalAuthorizeCode = true;
			}
		}
		return isLocalAuthorizeCode;
	}

	/**
	 * 是否授权码过期
	 * 
	 * @param partAuthorizeCode
	 *            部分授权码
	 * @return 是否授权码过期
	 */
	public static boolean isAuthorizeCodeExpired(String partAuthorizeCode) {
		try {
			String dateCode = partAuthorizeCode.substring(16, partAuthorizeCode.length());
			String dateString = Base64.getDecryptedString(Base64.END_DATE_KEY, dateCode);
			Date expiredDate = DateTimeUtils.formatDateString(dateString, DateTimeUtils.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
			DateTime expiredDatetime = new DateTime(expiredDate);
			if (expiredDatetime.isAfterNow()) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * 获取授权结束时间
	 * 
	 * @param partAuthorizeCode
	 *            部分授权码
	 * @return 授权结束时间
	 */
	public static String getAuthorizeCodeExpiredDate(String partAuthorizeCode) {
		String dateString = "";
		try {
			String dateCode = partAuthorizeCode.substring(16, partAuthorizeCode.length());
			dateString = Base64.getDecryptedString(Base64.END_DATE_KEY, dateCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateString;
	}

}
