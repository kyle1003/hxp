package cn.hurry.util;

import java.util.List;

public class ListUtil {
	/**
	 * 在对象集合中找到对象
	 * @param ObjectList 对象集合
	 * @param object 要找的对象
	 * @return 找到的对象
	 */
	public static synchronized Object findObjectInList(List<?> ObjectList, Object object) {
		for (Object object2 : ObjectList) {
			if (object.equals(object2)) {
				return object2;
			}
		}
		return null;
	}
}
