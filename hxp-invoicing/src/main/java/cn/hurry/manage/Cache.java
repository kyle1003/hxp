package cn.hurry.manage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hurry.util.ClassUtil;

/**
 * 数据缓存
 * 
 * @author ZhouHao
 * 
 */
public final class Cache implements Serializable {

	private static final long serialVersionUID = -3906412824014940988L;

	private static final Logger LOGGER = LoggerFactory.getLogger(Cache.class);

	/**
	 * 缓存Map
	 */
	private static final HashMap<String, HashMap<Object, Object>> CACHE_MAP = new HashMap<String, HashMap<Object, Object>>();

	/**
	 * 添加对象到缓存
	 * 
	 * @param key
	 *            缓存key
	 * @param id
	 *            对象编号
	 * @param object
	 *            添加的对象
	 */
	public static <T> void put(String key, Object id, T object) {
		synchronized (CACHE_MAP) {
			HashMap<Object, Object> value_map = CACHE_MAP.get(key);
			if (value_map == null)
				value_map = new HashMap<Object, Object>();
			value_map.put(id, object);
			CACHE_MAP.put(key, value_map);
		}
	}

	public static <T> void put(Object id, T object) {
		String key = object.getClass().getName();
		// 重新拼接CGLIB动态生成的类名
		if (key.contains("$$EnhancerByCGLIB")) {
			key = key.substring(0, key.indexOf("$$EnhancerByCGLIB"));
		}
		put(key, id, object);
	}

	/**
	 * 清空指定key缓存,在添加了缓存并使用后，应该手动清空
	 * 
	 * @param key
	 *            缓存key
	 */
	public static void clear(String key) {
		synchronized (CACHE_MAP) {
			CACHE_MAP.remove(key);
		}
	}

	/**
	 * 清空指定key缓存,在添加了缓存并使用后，应该手动清空
	 * 
	 * @param key
	 *            缓存key
	 */
	public static void clear(Class<?> key) {
		synchronized (CACHE_MAP) {
			CACHE_MAP.remove(key.getName());
		}
	}

	public static void remove(Class<?> key, Object id) {
		synchronized (CACHE_MAP) {
			HashMap<Object, Object> map = CACHE_MAP.get(key.getName());
			if (map != null)
				map.remove(id);
		}
	}

	/**
	 * 清空所有缓存
	 */
	public static void clearAll() {
		LOGGER.debug("清空缓存所有缓存");
		synchronized (CACHE_MAP) {
			CACHE_MAP.clear();
		}
		LOGGER.debug("清空缓存所有缓存成功");
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(Class<T> key, Object id) {
		return (T) get(key.getName(), id);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> get(Class<T> key) {
		synchronized (CACHE_MAP) {
			if (CACHE_MAP.get(key.getName()) == null) {
				return null;
			}
			return new ArrayList<T>((Collection<? extends T>) CACHE_MAP.get(key.getName()).values());
		}
	}

	/**
	 * 获取缓存对象
	 * 
	 * @param key
	 *            缓存key
	 * @param id
	 *            对象编号
	 * @return 编号对应的对象，没有则返回null
	 */
	public static Object get(String key, Object id) {
		synchronized (CACHE_MAP) {
			HashMap<Object, Object> value_map = CACHE_MAP.get(key);
			if (value_map == null) {
				LOGGER.error("获取缓存:key=" + key + ",id=" + id + "失败：key所指定的缓存不存在！");
				return null;
			}
			Object object = value_map.get(id);
			if (object == null)
				return null;
			try {
				// 复制出新对象，防止缓存对象属性被修改
				return ClassUtil.copyObject(object.getClass().newInstance(), object);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return object;
		}
	}
}
