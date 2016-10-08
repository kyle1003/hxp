package cn.hurry.util;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import flexjson.*;
import flexjson.transformer.*;

/**
 * 轻度依赖flexjson-2.0.jar 对JSON的自定义序列化与反序列化 <br>
 * 针对前端框架定制，如前端使用json解析为下拉列表或者树形菜单以及表格时
 * 
 * @author qq191154823
 * 
 */
public class JSON {

	private static final Logger logger =LoggerFactory.getLogger(JSON.class);
	/**
	 * 下拉列表默认JSON id 属性为id
	 */
	public static final String[] COMBOBOX_ID = { "id" };

	/**
	 * 下拉列表默认JSON text 属性为name
	 */
	public static final String[] COMBOBOX_TEXT = { "text", "name" };

	/**
	 * 树菜单默认JSON pid 属性为pid
	 */
	public static final String[] TREE_PID = { "pid" };
	public static final String[] TREE_EXPANDED = { "expanded", "false", "false" };
	public static final String[] TREE_ISLEAF = { "isLeaf", "false", "false" };
	public static final String[] TREE_FOLDER = { "folder", "1", "false" };

	/**
	 * JSON类型：表格数据格式
	 */
	public static final int TYPE_TABLE = 1;

	/**
	 * JSON类型：下拉列表数据格式
	 */
	public static final int TYPE_COMBOBOX = 3;

	/**
	 * JSON类型：树菜单数据格式
	 */
	public static final int TYPE_TREE = 2;

	/**
	 * 将对象json化 不推荐直接使用该方法 可能导致 延迟加载失效影响效率
	 * 
	 * @param obj
	 * @return
	 */
	public static String Encode(Object obj) {
		if (obj == null || obj.toString().equals("null"))
			return null;
		if (obj != null && obj.getClass() == String.class) {
			return obj.toString();
		}
		JSONSerializer serializer = new JSONSerializer();
		serializer.transform(new DateTransformer("yyyy-MM-dd"), Date.class);
		serializer.transform(new DateTransformer("yyyy-MM-dd"), Timestamp.class);
		return serializer.deepSerialize(obj);
	}

	public static void main(String[] args) {
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 1000; i++) {
			User user5 = new User();
			user5.setId(i);
			user5.setUsername("a" + i);
			user5.setDate(new Date());
//			user5.setFather(temp);
			users.add(user5);
		}
		// 单个对象解析
		// System.out.println(JsonEncoder.getInstance().encode(user, 20));
		long time = System.currentTimeMillis();
		System.out.println(Encode(users));
		// System.out.println(JsonEncoder.getInstance().encode(map, 20));
		System.out.println(System.currentTimeMillis()-time);
	}
	/**
	 * 将json对象化 不推荐直接使用该方法
	 * 
	 * @param json
	 * @return
	 */
	public static Object Decode(String json) {
		if (StringUtil.isNullOrEmpty(json))
			return "";
		JSONDeserializer<Object> deserializer = new JSONDeserializer<Object>();
		Object obj = deserializer.deserialize(json);
		if (obj != null && obj.getClass() == String.class) {
			return Decode(obj.toString());
		}
		return obj;
	}

	/**
	 * 根据数据类型获取JSON数据
	 * 
	 * @param data
	 *            对象集合
	 * @param dataSize
	 *            数据数量（用于分页）
	 * @param type
	 *            JSON数据类型
	 * @return 对应的JSON数据类型
	 * @throws Exception
	 */
	public static String objects2JsonByType(List<?> data, int dataSize, int type) throws Exception {
		switch (type) {
		// 如果需求的数据类型是表格
		case TYPE_TABLE:
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("data", objectList2HashMapList(data));
			result.put("total", dataSize);
			return JSON.Encode(result);
			// 如果需求的数据类型是下拉列表
		case TYPE_COMBOBOX:
			return JSON.Objects2Json(data, JSON.COMBOBOX_ID, JSON.COMBOBOX_TEXT);
			// 如果需求的数据类型是树菜单
		case TYPE_TREE:
			return JSON.Objects2Json(data, JSON.COMBOBOX_ID, JSON.COMBOBOX_TEXT, JSON.TREE_PID);
		default:
			return null;
		}
	}

	/**
	 * 根据属性名获取getter、setter方法
	 * 
	 * @param getOrSet
	 *            get方法或者set方法
	 * @param field
	 *            属性名称
	 * @return 如：getOrSet为 set field为name 返回 setName
	 */
	private static String encodeGetSetMethod(String getOrSet, String field) {
		StringBuilder builder = new StringBuilder();
		builder.append(getOrSet);
		builder.append(field.substring(0, 1).toUpperCase());
		builder.append(field.substring(1, field.length()));
		return builder.toString();
	}

	/**
	 * 对象转为JSON 指定JSON中key名称和对象的属性
	 * 
	 * @param object
	 *            对象
	 * @param field
	 *            指定JSON格式
	 *            <ul>
	 *            <li>field为不定长度的字符串参数,单个参数为字符串数组 长度最少为2</li>
	 *            <li>field[0]的值为JSON中key的值,field[1]的值为对象中属性的值（属性必须有get方法）没有此方法或者属性错误时 ,值为属性名</li>
	 *            <li>属性目前只支持普通数据类型和时间类型,其他自定义类型暂时不支持。时间类型可指定格式，field[2] 为时间格式</li>
	 *            </ul>
	 * @return 例如:object对象为 User,有属性id,name;值分别为10,admin<br>
	 *         field参数为 {id,id},{text,name},{icon,folder}<br>
	 *         解析后返回为{"id":"10","text":"admin","icon":"user"}; icon,folder中folder并不是user对象的属性 所以值默认为folder
	 */
	public static String Object2Json(Object object, String[]... field) {
		StringBuilder builder = new StringBuilder();
		boolean isString = true;
		String key = null;
		String filedKey = null;
		Object value = null;
		builder.append("{");
		// 遍历参数列表
		for (int i = 0, s = field.length; i < s; i++) {
			isString = true;// 是否以字符串形式如：id=1 为true时格式为 "id":"1" false时为,"id":1 默认为true
			key = field[i][0];// JSON中的key
			filedKey = key;// JSON中key对应对象的属性 默认为json中的key
			if (field[i].length > 1) {
				filedKey = field[i][1];
			}
			// 是否为字符串形式
			if (field[i].length > 2 && field[i][2].equals("false")) {
				isString = false;
			}
			value = filedKey;// JSON中的value 默认为属性名称
			try {
				// 获取get方法
				Method method = object.getClass().getMethod(encodeGetSetMethod("get", filedKey));
				value = method.invoke(object);
				// 格式化时间类型
				if (method.getReturnType().getName().equals(Date.class.getName())) {
					String dateformat = field[i].length > 2 ? field[i][2] : DateTimeUtils.YEAR_MONTH_DAY;
					value = DateTimeUtils.format((Date) value, dateformat);
				}
			} catch (Exception e) {
			}
			if (isString) {
				builder.append("\"" + key + "\":\"" + formatErrorString(value) + "\"");
			} else {
				builder.append("\"" + key + "\":" + formatErrorString(value));
			}
			if (i < s - 1) {
				builder.append(",");
			}
		}
		builder.append("}");
		return builder.toString();
	}

	/**
	 * 替换JSON中存在的非法字符 如"
	 * 
	 * @param str
	 * @return
	 */
	public static String formatErrorString(Object str) {
		return str == null ? null : str.toString().replace("\"", "\\\"").replace("<", "&lt;").replace(">", "&gt;").replace(",", "\\,");
	}

	/**
	 * 对象集合转为自定义JSON 指定JSON中key名称和对象的属性
	 * 
	 * @param objects
	 *            对象集合
	 * @param field
	 *            指定JSON格式
	 *            <ul>
	 *            <li>field为不定长度的字符串参数,单个参数为字符串数组 长度最少为2</li>
	 *            <li>
	 *            field[0]的值为JSON中key的值,field[1]的值为对象中属性的值（属性必须有get方法）没有此方法或者属性错误时 ,值为属性名</li>
	 *            <li>属性目前只支持普通数据类型和时间类型,其他自定义类型暂时不支持。时间类型可指定格式，field[2] 为时间格式</li>
	 *            </ul>
	 * @return 例如:objects集合中有一个对象 User,有属性id,name;值分别为10,admin<br>
	 *         field参数为 {id,id},{text,name},{icon,folder}<br>
	 *         解析后返回为[{"id":"10","text":"admin","icon":"user"}]; icon,folder中folder并不是user对象的属性 所以值默认为folder
	 */
	public static String Objects2Json(List<?> objects, String[]... field) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for (int l = 0, sz = objects.size(); l < sz; l++) {
			Object object = objects.get(l);
			builder.append(Object2Json(object, field));
			if (l < sz - 1) {
				builder.append(",");
			}
		}
		builder.append("]");
		return builder.toString();
	}

	/**
	 * 将JSON转换为hashMap集合
	 */
	@SuppressWarnings("unchecked")
	public static List<HashMap<?, ?>> json2list(String json) throws Exception {
		List<HashMap<?, ?>> maplist = new ArrayList<HashMap<?, ?>>();
		Object object = JSON.Decode(json);
		if (object instanceof HashMap) {
			maplist.add((HashMap<?, ?>) object);
		}
		if (object instanceof ArrayList) {
			return (List<HashMap<?, ?>>) object;
		}
		return maplist;
	}

	/**
	 * JSON数据转为对象 key对应对象的属性 必须有set方法 <br>
	 * 如果JSON中包含多条数据 则无法转换
	 * 
	 * @param json
	 *            JSON数据
	 * @param type
	 *            转换的对象类型
	 * @return 实例化后的对象
	 * @throws Exception
	 */
	public static <T> T Json2Object(String json, Class<T> type) throws Exception {
		List<HashMap<?, ?>> dataList = json2list(json);// 将JSON数据转换为hashMap集合
		if (dataList.size() == 1) {// 转换单个对象，多个对象为集合
			HashMap<?, ?> data = dataList.get(0);// 获取第一个对象
			return hashMap2Object(data, type); // 返回实例化的对象
		} else {
			throw new Exception("json中存在多个数据,无法转型为" + type.getClass().getName());
		}
		// if (dataList.size() > 1) {// 转换多个对象为arrayList集合
		// List<Object> objList = new ArrayList<Object>();
		// for (HashMap<?, ?> data : dataList) {
		// objList.add(hashMap2Object(data, type));
		// }
		// return objList;
		// }
		// return null;
	}

	/**
	 * JSON数据转换为集合
	 * 
	 * @param json
	 *            JSON数据
	 * @param type
	 *            类型
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> Json2Objects(String json, Class<T> type) throws Exception {
		List<HashMap<?, ?>> dataList = json2list(json);// 将JSON数据转换为hashMap集合
		List<T> list = new ArrayList<T>();
		if (dataList.size() == 1) {// 转换单个对象，多个对象为集合
			HashMap<?, ?> data = dataList.get(0);// 获取第一个对象
			list.add(hashMap2Object(data, type)); // 返回实例化的对象
			return list;
		}
		if (dataList.size() > 1) {// 转换多个对象为arrayList集合
			List<T> objList = new ArrayList<T>();
			for (HashMap<?, ?> data : dataList) {
				objList.add(hashMap2Object(data, type));
			}
			return objList;
		}
		return null;
	}
	
	public static <T> List<T> HashMapList2Objects(ArrayList<HashMap<?, ?>> datalist, Class<T> type) throws Exception {
		List<T> list = new ArrayList<T>();
		if (datalist.size() == 1) {// 转换单个对象，多个对象为集合
			HashMap<?, ?> data = datalist.get(0);// 获取第一个对象
			list.add(hashMap2Object(data, type)); // 返回实例化的对象
			return list;
		}
		if (datalist.size() > 1) {// 转换多个对象为arrayList集合
			List<T> objList = new ArrayList<T>();
			for (HashMap<?, ?> data : datalist) {
				objList.add(hashMap2Object(data, type));
			}
			return objList;
		}
		return null;
	}

	/**
	 * HashMap转为对象 hashMap中key对应 对象中属性 值对应调用set方法传入的值
	 * 
	 * @param data
	 *            HashMap数据
	 * @param objectClass
	 *            要转换的对象类型
	 * @return 转换后的对象
	 * @throws InstantiationException
	 */
	public static <T> T hashMap2Object(HashMap<?, ?> data, Class<T> objectClass) throws InstantiationException, IllegalAccessException {
		T obs = objectClass.newInstance();// 实例化
		// 获取所有set方法
		for (Method method : ClassUtil.getSetMethod(objectClass)) {
			Class<?>[] parmType = method.getParameterTypes();// 方法参数类型
			Object parmObj = data.get(methodName2Attr(method.getName()));// JSON中对应set方法的value
			if (parmObj != null) {// JSON中没有
				String str = parmObj.toString();// 值
				Object[] prams = { ClassUtil.String2ObjectByType(str, parmType[0].getName()) };// 参数列表 暂时 只支持单个参数
				try {
					method.invoke(obs, prams);// 执行方法
				} catch (NumberFormatException e) {
					throw e;
				} catch (Exception e) {
					// do nothing
				}
			}
		}
		return obs; // 返回实例化的对象
	}

	/**
	 * 将get set方法名转为属性名
	 * 
	 * @param methodName
	 *            get set方法名
	 * @return
	 */
	private static String methodName2Attr(String methodName) {
		return methodName.substring(3).substring(0, 1).toLowerCase() + methodName.substring(4);
	}

	/**
	 * 对一个对象集合进行转换 将字符串以键值对形式存入map后重新装入集合。对象需要重写toString方法。格式为key=value,key=value <br>
	 * 通过调用toString方法 键值对自定义
	 * 
	 * @param 要进行转换的字符串集合
	 * @return 转换后的集合
	 * @throws Exception
	 */
	public static List<HashMap<?, ?>> objectList2HashMapList(List<?> list) throws Exception {
		ArrayList<HashMap<?, ?>> data = new ArrayList<HashMap<?, ?>>();
		for (int i = 0, l = list.size(); i < l; i++) {
			// 将单个数据转换为hashmap
			HashMap<?, ?> record = object2HashMap(list.get(i));
			if (record == null) {
				continue;
			}
			data.add(record);
		}
		return data;
	}

	/**
	 * 将JSON转换为hashMap集合
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<?, ?> json2hashMap(String json) throws Exception {
		Object object = JSON.Decode(json);
		if (object instanceof HashMap) {
			return (HashMap<?, ?>) object;
		} else {
			throw new Exception("json包含的数据有多个，不能转换为单个hashMap");
		}
	}

	/**
	 * 单个对象转为自定义JSON，需要重写toString方法
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static HashMap<?, ?> object2HashMap(Object object) throws Exception {
		// 将单个数据转换为hashmap
		HashMap<?, ?> record = null;
		try {
			record = StringUtil.string2HashMap(object.toString());
		} catch (Exception e) {
			logger.error("解析json错误:",e);
		}
		return record;
	}

	public static String object2Json(Object object) throws Exception {
		return Encode(object2HashMap(object)).replace("&", "＆");
	}

}
