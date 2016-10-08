package cn.hurry.util;

import java.io.File;
import java.io.FileInputStream;

/**
 * 图片工具类
 * 
 * @author zh.sqy@qq.com
 * 
 */
public class ImageUtil {

	/**
	 * 获取图片数据
	 * 
	 * @param imagePath
	 *            图片地址
	 * @return 图片数据
	 * @throws Exception
	 */
	public static byte[] getImage(String imagePath) throws Exception {
		File file = new File(imagePath);
		FileInputStream fis = null;
		byte[] imageData = null;
		try {
			fis = new FileInputStream(file);
			imageData = new byte[(int) file.length()];
			fis.read(imageData, 0, imageData.length);
		} catch (Exception e) {
			throw e;
		} finally {
			CommonIOUtils.closeQuietly(fis);
			fis = null;
		}
		return imageData;
	}
}
