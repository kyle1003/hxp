package cn.hurry.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 将汉字转化为全拼
 */
public class StringUtil {

	/**
	 * 获取汉字全拼
	 * 
	 * @param str
	 *            汉字字符串
	 * @return 汉字字符串全拼
	 */
	public static String getPingYin(String str) {
		char[] t1 = null;
		t1 = str.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					t4 += t2[0];
				} else {
					t4 += java.lang.Character.toString(t1[i]);
				}
			}
			return t4;
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		return t4;
	}

	/**
	 * 获取中文字符串首字母
	 * 
	 * @param str
	 *            字符串
	 * @return 字符串首字母
	 */
	public static String getPinYinHeadChar(String str) {
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
	}

	/**
	 * 将字符串转移为ASCII码
	 * 
	 * @param str
	 *            字符串
	 * @return 字符串ASCII码
	 */
	public static String getCnASCII(String str) {
		StringBuffer strBuf = new StringBuffer();
		byte[] bGBK = str.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

	/**
	 * 对象是否为无效值
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNullOrEmpty(Object obj) {
		return obj == null || "".equals(obj.toString())|| "null".equals(obj.toString());
	}

	/**
	 * 将对象转为String
	 * 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		if (obj == null)
			return "null";
		return obj.toString();
	}

	@SuppressWarnings("unchecked")
	public static String join(Collection s, String delimiter) {
		StringBuffer buffer = new StringBuffer();
		Iterator iter = s.iterator();
		while (iter.hasNext()) {
			buffer.append(iter.next());
			if (iter.hasNext()) {
				buffer.append(delimiter);
			}
		}
		return buffer.toString();
	}

	/**
	 * 将字符串转换为hashmap
	 * 
	 * @param str
	 *            要转换的字符串 格式为 key=value,key2=value2
	 * @return 转换后的map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static HashMap string2HashMap(String str) throws Exception {
		HashMap map = new HashMap();
		// 截取,分隔符
		String mstr[] = str.split(",");
		for (int x = 0; x < mstr.length; x++) {
			String key = mstr[x].substring(0, mstr[x].indexOf("="));
			String value = mstr[x].substring(mstr[x].indexOf("=") + 1, mstr[x].length());
			map.put(key, value);
		}
		return map;
	}

	/**
	 * 字符串参数是否是整数
	 * 
	 * @param str字符串
	 * @return 是否是整数
	 */
	public static boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 字符串参数是否是double
	 * 
	 * @param str
	 *            字符串
	 * @return 是否是double
	 * @throws Exception
	 */
	public static boolean isDouble(Object str) throws Exception {
		try {
			if (isNullOrEmpty(str)) {
				return false;
			}
			Double.parseDouble(str.toString());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断数组strArr中是否包含一个元素
	 * 
	 * @param strArr
	 *            字符串数组
	 * @param str
	 *            字符串
	 * @return 如果有返回true没有返回false
	 */
	public static boolean containObjInArr(Object strArr[], Object obj) {
		if (!StringUtil.isNullOrEmpty(strArr)) {
			for (int i = 0; i < strArr.length; i++) {
				if (strArr[i].equals(obj) || strArr[i] == obj) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean containIntInArr(int arr[], int x) {
		if (!StringUtil.isNullOrEmpty(arr)) {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] == x) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 拆分逗号隔开的数字字符串到int数组
	 * 
	 * @param appendString
	 *            数字字符串
	 * 
	 * @return int数组
	 */
	public static int[] getSplitArray(String appendString) {
		if (appendString != null && appendString.length() > 0) {
			appendString = appendString.trim();
			String[] stringArray = appendString.split(",");
			int[] intArray = new int[stringArray.length];
			for (int i = 0; i < stringArray.length; i++) {
				intArray[i] = Integer.parseInt(stringArray[i]);
			}
			return intArray;
		}
		return new int[0];
	}

	/**
	 * 组合int数组的值为逗号隔开的数字字符串
	 * 
	 * @param splitArray
	 *            int数组
	 * 
	 * @return 逗号隔开的数字字符串
	 */
	public static String getAppendStringFromArray(int[] splitArray) {
		if (splitArray != null && splitArray.length > 0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < splitArray.length; i++) {
				sb.append(splitArray[i]);
				if (i != splitArray.length - 1) {
					sb.append(",");
				}
			}
			return sb.toString();
		}
		return "";
	}

	/**
	 * 拆分逗号隔开的数字字符串到Integer列表
	 * 
	 * @param appendString
	 *            数字字符串
	 * 
	 * @return Integer列表
	 */
	public static ArrayList<Integer> getSplitList(String appendString) {
		if (appendString != null && appendString.length() > 0) {
			appendString = appendString.trim();
			String[] stringArray = appendString.split(",");
			ArrayList<Integer> integerList = new ArrayList<Integer>(stringArray.length);
			for (int i = 0; i < stringArray.length; i++) {
				integerList.add(Integer.parseInt(stringArray[i]));
			}
			return integerList;
		}
		return new ArrayList<Integer>(0);
	}

	/**
	 * 组合int数组的值为逗号隔开的数字字符串
	 * 
	 * @param splitArray
	 *            int数组
	 * 
	 * @return 逗号隔开的数字字符串
	 */
	public static String getAppendStringFromList(ArrayList<Integer> integerList) {
		if (integerList != null && integerList.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0, size = integerList.size(); i < size; i++) {
				sb.append(integerList.get(i));
				if (i != size - 1) {
					sb.append(",");
				}
			}
			return sb.toString();
		}
		return "";
	}
	

	public static void main(String[] args) {
		//System.out.println("\u4e02");
//		for(int i=0x4e00;i<0x9fa5;i++){
//			try {
//				System.out.print((char)i);
//				System.out.println("="+getPingYin((char)i+""));
//			} catch (Exception e) {
//			}
//		}
//		System.out.println(0x9fa5-0x4e00);
		
		System.out.println(chinaToUnicode("联系电话"));
	}
	
	public static String chinaToUnicode(String str){  
        String result="";  
        for (int i = 0; i < str.length(); i++){  
            int chr1 = (char) str.charAt(i);  
            if(chr1>=0x4e00&&chr1<=171941){//汉字范围 \u4e00-\u9fa5
                result+="\\u" + Integer.toHexString(chr1);  
            }else{  
                result+=str.charAt(i);  
            }  
        }  
        return result;  
    }  

}