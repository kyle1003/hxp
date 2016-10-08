package cn.hurry.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.io.Resources;

/**
 * properties文件读取工具类
 * 
 * @author zh.sqy@qq.com
 * 
 * @version $Revision: 208 $ <br>
 *          $LastChangedBy: zh.sqy@qq.com $ <br>
 *          $LastChangedDate: 2012-03-12 16:35:19 +0800 (Mon, 12 Mar 2012) $
 */
public class PropertiesUtil {

	static Properties pro = null;

	/**
	 * 根据properties文件的名称，以及键获取值(ps:properties文件必须放在src目录下)
	 * 
	 * @param propertiesFileName
	 *            properties文件的名称
	 * @param key
	 *            键
	 * @return 值
	 */
	public static String getValueByKey(String propertiesFileName, String key) {
		pro = new Properties();
		InputStream is = null;
		try {
			is = Resources.getResourceAsStream(propertiesFileName + ".properties");
			pro.load(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pro.getProperty(key);
	}

	/**
	 * 根据properties文件的名称，获取该文件中的所有键并放入一个map中(ps:properties文件必须放在src目录下)
	 * 
	 * @param propertiesFileName
	 *            properties文件的名称
	 * @return
	 */
	public static HashMap<String, String> getMapByFileName(String propertiesFileName) {
		pro = new Properties();
		InputStream is = null;
		try {
			is = Resources.getResourceAsStream(propertiesFileName + ".properties");
			pro.load(is);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		HashMap<String, String> map = new HashMap<String, String>();
		Set<String> e = pro.stringPropertyNames();
		Iterator<String> it = e.iterator();
		while (it.hasNext()) {
			String key = it.next();
			map.put(key, pro.getProperty(key));
		}
		return map;
	}
}
