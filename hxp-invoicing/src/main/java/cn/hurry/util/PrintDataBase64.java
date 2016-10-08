package cn.hurry.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Base64加密解密工具
 * 
 * @author zh.sqy@qq.com
 * 
 */
public class PrintDataBase64 {

	/**
	 * 结束日期加密KEY
	 */
	public static String END_DATE_KEY = "hurryprint@of*developer&data";

	/**
	 * 总KEY
	 */
	public static String ALL_KEY = "hurryprint@of*developer";

	/**
	 * 获得密钥
	 * 
	 * @param str
	 *            生成密钥的字符串
	 * @return 密钥
	 * @throws Exception
	 */
	private static SecretKey getKey(String str) throws Exception {
		char[] ss = str.toCharArray();
		String sss = "";
		for (int i = 0; i < ss.length; i = i + 2) {
			sss = sss + ss[i];
		}
		SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");
		DESKeySpec ks = new DESKeySpec(sss.substring(0, 8).getBytes());
		SecretKey kd = kf.generateSecret(ks);
		return kd;
	}

	/**
	 * 加密字符串
	 * 
	 * @param key
	 *            用于生成密钥的字符串
	 * @param input
	 *            要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String getEncryptedString(String key, String input) {
		String base64 = "";
		try {
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, getKey(key));
			byte[] inputBytes = input.getBytes("UTF8");
			byte[] outputBytes = cipher.doFinal(inputBytes);
			BASE64Encoder encoder = new BASE64Encoder();
			base64 = encoder.encode(outputBytes);
		} catch (Exception e) {
			base64 = e.getMessage();
		}
		return base64.replace("=", "-");
	}

	/**
	 * 解密字符串
	 * 
	 * @param key
	 *            用于生成密钥的字符串
	 * @param input
	 *            要解密的字符串
	 * @return 解密后的字符串
	 */
	public static String getDecryptedString(String key, String input) {
		String result = null;
		input=input.replace("-", "=").replace(" ", "+");
		try {
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, getKey(key));
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] raw = decoder.decodeBuffer(input);
			byte[] stringBytes = cipher.doFinal(raw);
			result = new String(stringBytes, "UTF8");
		} catch (Exception e) {
			result = e.getMessage();
		}
		return result;
	}

}
