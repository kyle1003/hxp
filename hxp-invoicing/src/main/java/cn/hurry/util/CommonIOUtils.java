package cn.hurry.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;

/**
 * 常用IO工具类
 * 
 * @author zh.sqy@qq.com
 * 
 */
public final class CommonIOUtils extends IOUtils {

	/**
	 * 用GZIP压缩字节数组
	 * 
	 * @param bytes
	 *            字节数组
	 * @return 压缩后的字节数组
	 */
	public static byte[] encodeGzipBytes(byte[] bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPOutputStream gos = null;
		try {
			gos = new GZIPOutputStream(baos);
			gos.write(bytes, 0, bytes.length);
			gos.finish();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(gos);
		}
		byte[] gzipBytes = baos.toByteArray();
		IOUtils.closeQuietly(baos);
		return gzipBytes;
	}

	/**
	 * 关闭Socket连接
	 * 
	 * @param socket
	 *            Socket连接
	 */
	public static void closeQuietly(Socket socket) {
		try {
			if (socket != null) {
				socket.close();
			}
		} catch (IOException ioe) {
		}
	}
}
