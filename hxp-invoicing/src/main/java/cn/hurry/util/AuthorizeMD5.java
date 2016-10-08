package cn.hurry.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 授权MD5
 * 
 * @author zh.sqy@qq.com
 * 
 */
public class AuthorizeMD5 {

	/**
	 * MD5 加密（返回32位）
	 * 
	 * @param str
	 *            加密内容
	 * @return String
	 */
	public static String getMD5By32(String str) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5"); // 生成实现指定摘要算法的 MessageDigest 对象
			md.reset(); // 重置摘要以供再次使用
			md.update(str.getBytes("UTF-8")); // 使用指定的字节数组更新摘要
		} catch (NoSuchAlgorithmException e) {
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 通过执行诸如填充之类的最终操作完成哈希计算。调用此方法后摘要被重置
		byte[] byteArray = md.digest();
		StringBuffer buf = new StringBuffer();
		// toHexString() 以十六进制的无符号整数形式返回一个整数参数的字符串表示形式
		for (int i = 0; i < byteArray.length; i++) {
			// 0xff == 去掉高位，只保留低16位
			// 0x代表十六进制
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				// 将 int 参数的字符串表示形式追加到此序列
				buf.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else {
				buf.append(Integer.toHexString(0xFF & byteArray[i]));
			}
			// 10进制255 == 16进制ff
			// int num = byteArray[i];
			// if(num < 0) {
			// num+= 256;
			// }
			// if(num < 16) {
			// buf.append("0");
			// }
			// buf.append(Integer.toHexString(num));
		}
		return buf.toString();
	}

	/**
	 * MD5 加密（返回16位）
	 * 
	 * @param str
	 *            加密内容
	 * @return String
	 */
	public static String getMD5By16(String str) {

		String retString = null;

		retString = getMD5By32(str);

		retString = retString.substring(8, 24);

		return retString;
	}
}
