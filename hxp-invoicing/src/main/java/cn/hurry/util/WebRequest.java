package cn.hurry.util;

import java.net.*;
import java.io.*;

public class WebRequest {

	/**
	 * 以POST方式请求
	 * 
	 * @param url
	 *            要请求的地址
	 * @param data
	 *            请求参数 格式为 param1=value1&amp;param2=value2&amp;.....
	 * @param charset
	 *            字符集
	 * @return 响应结果
	 */
	public static String post(String url, String data, String charset) {
		try {
			URL __url = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) __url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStreamWriter bos = new OutputStreamWriter(conn.getOutputStream(), charset);
			bos.write(data);
			bos.flush();
			BufferedReader bis = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = bis.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			bis.close();
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 以post方式，默认字符集UTF-8 请求
	 * 
	 * @param url
	 *            请求地址
	 * @param data
	 *            请求参数 格式为 param1=value1&amp;param2=value2&amp;.....
	 * @return 请求结果
	 */
	public static String post(String url, String data) {
		return post(url, data, "utf-8");
	}

	/**
	 * 以get方式请求
	 * 
	 * @param url
	 *            要请求的地址
	 * @param charset
	 *            字符集
	 * @return 请求结果
	 */
	public static String get(String url, String charset) {
		try {
			URL __url = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) __url.openConnection();
			BufferedReader bis = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = bis.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			bis.close();
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 请求文件（大文件慎用）
	 * 
	 * @param url
	 *            文件地址
	 * @return
	 */
	public static byte[] file(String url) {
		try {
			URL __url = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) __url.openConnection();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			byte[] b = new byte[1024];
			int length = -1;
			while ((length = bis.read(b)) != -1) {
				bos.write(b, 0, length);
				bos.flush();
			}
			bis.close();
			bos.close();
			return bos.toByteArray();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取指定url文件并输出给指定输出流
	 * 
	 * @param url
	 *            文件url路径
	 * @param out
	 *            输出流对象 (会自动转为BufferedOutputStream 后输出)
	 * @throws Exception
	 *             访问或者输出异常
	 */
	public static void file(String url, OutputStream out) throws Exception {
		URL __url = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) __url.openConnection();
		BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
		BufferedOutputStream os = new BufferedOutputStream(out);
		byte[] b = new byte[1024];
		int length = -1;
		while ((length = bis.read(b)) != -1) {
			os.write(b, 0, length);
			os.flush();
		}
		bis.close();
		os.close();
	}

	public static void main(String[] args) {
	}

}
