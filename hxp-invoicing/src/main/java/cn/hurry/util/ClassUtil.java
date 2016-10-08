package cn.hurry.util;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClassUtil {

	private static final String[] baseClassName = { byte.class.getName(), double.class.getName(), float.class.getName(), byte.class.getName(),
			int.class.getName(), char.class.getName(), boolean.class.getName(), long.class.getName(), short.class.getName(), Long.class.getName(),
			Short.class.getName(), Class.class.getName(), Byte.class.getName(), Double.class.getName(), Float.class.getName(), Byte.class.getName(),
			Integer.class.getName(), Charset.class.getName(), Boolean.class.getName(), String.class.getName(), Date.class.getName() };

	/**
	 * 根据属性名获取getter、setter方法
	 * 
	 * @param getOrSet
	 *            get方法或者set方法
	 * @param field
	 *            属性名称
	 * @return 如：getOrSet为 set field为name 返回 setName
	 */
	public static String encodeGetSetMethod(String getOrSet, String field) {
		StringBuilder builder = new StringBuilder();
		builder.append(getOrSet);
		builder.append(field.substring(0, 1).toUpperCase());
		builder.append(field.substring(1, field.length()));
		return builder.toString();
	}

	public static String getAttributeByGetMehodName(String field) {
		StringBuilder builder = new StringBuilder();
		field = field.substring(3);
		builder.append(field.substring(0, 1).toLowerCase());
		builder.append(field.substring(1, field.length()));
		return builder.toString();
	}

	// public static void main(String[] args) {
	// List<Method> methods = getGetMethod(User.class);
	// try {
	// for (Method method : methods) {
	// String className = getReturnTypeClassName(method);
	// System.out.println(className);
	// System.out.println(isBaseObj(className));
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// // System.out.println(method2.getName());
	// }

	public static boolean isBaseObj(String className) throws Exception {
		for (int i = 0, size = baseClassName.length; i < size; i++) {
			if (baseClassName[i].equals(className)) {
				return true;
			}
		}
		return false;
	}

	public static String getReturnTypeClassName(Method method) {
		Class<?> returnType = method.getReturnType();
		return getClassFullName(returnType);
	}

	public static String getClassFullName(Class<?> class1) {
		String className = class1.getPackage().getName() + "." + class1.getSimpleName();
		return className;
	}

	public static List<Method> getGetMethod(Class<?> classT) {
		return getMethodByIndexOf(classT, "get");
	}

	public static List<Method> getSetMethod(Class<?> classT) {
		return getMethodByIndexOf(classT, "set");
	}

	public static List<Method> getMethodByIndexOf(Class<?> classT, String indexOf) {
		Method[] m = classT.getMethods();
		List<Method> methods = new ArrayList<Method>();
		Method method = null;
		for (int i = 0, size = m.length; i < size; i++) {
			method = m[i];
			if (method.getName().indexOf(indexOf) == 0) {
				methods.add(method);
			}
		}
		return methods;
	}

	/**
	 * 将字符串str转换为typeName对应的数据类型 只支持基本数据类型byte,int,double,float,boolean,Date
	 * 
	 * @param str
	 *            字符串str
	 * @param typeName
	 *            数据类型名称typeName 如int java.lang.Ineger
	 * @return
	 */
	public static Object String2ObjectByType(String str, String typeName) {
		if (typeName.equals("byte") || typeName.equals(Byte.class.getName())) {
			try {
				return Byte.parseByte(str);
			} catch (Exception e) {
				return (byte) 0;
			}
		} else if (typeName.equals("int") || typeName.equals(Integer.class.getName())) {
			try {
				return Integer.parseInt(str);
			} catch (Exception e) {
				return 0;
			}
		} else if (typeName.equals("double") || typeName.equals(Double.class.getName())) {
			try {
				return Double.parseDouble(str);
			} catch (Exception e) {
				return 0d;
			}
		} else if (typeName.equals("float") || typeName.equals(Float.class.getName())) {
			try {
				return Float.parseFloat(str);
			} catch (Exception e) {
				return 0f;
			}
		} else if (typeName.equals("boolean") || typeName.equals(Boolean.class.getName())) {
			try {
				return Boolean.parseBoolean(str);
			} catch (Exception e) {
				return false;
			}
		} else if (typeName.equals(Date.class.getName())) {
			try {
				return DateTimeUtils.formatUnknownString2Date(str);
			} catch (Exception e) {
				return null;
			}
		} else {
			return str;
		}
	}

	public static String getGetOrSetMethodByAttribute(String getOrSet, String attribute) {
		StringBuilder builder = new StringBuilder();
		builder.append(getOrSet);
		builder.append(attribute.substring(0, 1).toUpperCase());
		builder.append(attribute.substring(1, attribute.length()));
		return builder.toString();

	}

	/**
	 * 通过反射，复制对象。复制时会遍历to参数的
	 * 
	 * @param <T>
	 *            复制对象类型为泛型
	 * @param to
	 *            复制到的对象
	 * @param source
	 *            复制源
	 * @return 复制后的对象
	 * @throws Exception
	 *             复制失败错误信息
	 */
	public static <T> T copyObject(T to, T source) throws Exception {
		List<Method> methods = getSetMethod(to.getClass());
		for (Method sourceMethod : methods) {
			String atrName = getAttributeByGetMehodName(sourceMethod.getName());
			String fsetMethod = null;
			if (sourceMethod.getParameterTypes()[0].getName().equalsIgnoreCase(boolean.class.getName())) {
				fsetMethod = getGetOrSetMethodByAttribute("is", atrName);
			} else {
				fsetMethod = getGetOrSetMethodByAttribute("get", atrName);
			}
			try {
				Method fromMethod = source.getClass().getMethod(fsetMethod);
				Object value = fromMethod.invoke(source);
				String setMethod = getGetOrSetMethodByAttribute("set", atrName);
				Method sourceSetMentod = to.getClass().getMethod(setMethod, fromMethod.getReturnType());
				sourceSetMentod.invoke(to, value);
			} catch (Exception e) {
				// 调取方法错误时跳过此方法
				continue;
			}
		}
		return to;
	}

}
