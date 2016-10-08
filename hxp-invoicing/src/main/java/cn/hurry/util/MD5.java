package cn.hurry.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密类
 * 
 * @author zh.sqy@qq.com
 * 
 * @version $Revision: 208 $ <br>
 *          $LastChangedBy: zh.sqy@qq.com $ <br>
 *          $LastChangedDate: 2012-03-12 16:35:19 +0800 (Mon, 12 Mar 2012) $
 */
public final class MD5 {

	/**
	 * 把字符串进行MD5加密
	 * 
	 * @param string
	 *            字符串
	 * 
	 * @return MD5加密后的字符串
	 */
	public static String encode(String string) {
		String encode = defaultEncode(string);
		StringBuilder sb = new StringBuilder();
		sb.append("@Win");
		for (int i = 0, length = encode.length() / 2; i < length; i++) {
			sb.append(encode.charAt(i * 2 + 1));
			sb.append(encode.charAt(i * 2));
		}
		sb.append("KaiFa$");
		return defaultEncode(sb.toString());
	}
	

	/**
	 * 把字符串进行MD5加密
	 * 
	 * @param string
	 *            字符串
	 * 
	 * @return MD5加密后的字符串
	 */
	private static String defaultEncode(String string) {
		StringBuilder sb = new StringBuilder(32);

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			byte[] hashValue = md.digest(string.getBytes());
			for (int i = 0; i < hashValue.length; i++) {
				sb.append(Integer.toHexString((hashValue[i] & 0xf0) >> 4));
				sb.append(Integer.toHexString(hashValue[i] & 0x0f));
			}
		} catch (NoSuchAlgorithmException e) {
		}

		return sb.toString();
	}

}
