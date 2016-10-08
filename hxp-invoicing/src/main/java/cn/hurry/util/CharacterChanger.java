package cn.hurry.util;

/**
 * 字符替换类
 * 
 * @author zh.sqy@qq.com
 * 
 */
public class CharacterChanger {

	/**
	 * 将字符串中相应字符替换成html适应的字符
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String changeHTMLCharacter(String str) {
		str = str.replaceAll("\n", "<br/>");
		str = str.replaceAll(" ", "&nbsp;");
		return str;
	}

	/**
	 * 将字符串中相应HTML字符替换成文本域适应的字符
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String changeHTMLBack(String str) {
		str = str.replaceAll("<br/>", "\n");
		str = str.replaceAll("&nbsp;", " ");
		return str;
	}

	/**
	 * 将字符串中相应HTML字符替换成文本域适应的字符
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String changeHTMLBackWap(String str) {
		str = str.replaceAll("&nbsp;", " ");
		return str;
	}

	/**
	 * 字符串替换
	 * 
	 * @param strSource
	 *            字符串源
	 * @param strFrom
	 *            要替换的字符串
	 * @param strTo
	 *            替换成的字符串
	 * @return
	 */
	public static String replace(String strSource, String strFrom, String strTo) {
		if (strSource == null) {
			return null;
		}
		int i = 0;
		if ((i = strSource.indexOf(strFrom, i)) >= 0) {
			char[] cSrc = strSource.toCharArray();
			char[] cTo = strTo.toCharArray();
			int len = strFrom.length();
			StringBuffer buf = new StringBuffer(cSrc.length);
			buf.append(cSrc, 0, i).append(cTo);
			i += len;
			int j = i;
			while ((i = strSource.indexOf(strFrom, i)) > 0) {
				buf.append(cSrc, j, i - j).append(cTo);
				i += len;
				j = i;
			}
			buf.append(cSrc, j, cSrc.length - j);
			return buf.toString();
		}
		return strSource;
	}
	
	/**
	 * 转换转义符
	 * @param text
	 * @return
	 */
	public static String encodeHTML(String text){
		if(!StringUtil.isNullOrEmpty(text)){
			text = text.replace("&", "&amp;");
			text = text.replace("<", "&lt;");
			text = text.replace(">", "&gt;");
			text = text.replace("\"", "&quot;");
		}
		return text;
	}
	
	/**
	 * 反转换转义符
	 * @param text
	 * @return
	 */
	public static String rollEncodeHTML(String text){
		if(!StringUtil.isNullOrEmpty(text)){
			text = text.replace("&amp;", "&");
			text = text.replace("&lt;", "<");
			text = text.replace("&gt;", ">");
			text = text.replace("&quot;", "''");
		}
		return text;
	}

}
