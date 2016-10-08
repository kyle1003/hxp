package cn.hurry.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * MAC地址工具
 * 
 * @author zh.sqy@qq.com
 * 
 */
public class MacUtil {

	/**
	 * 获取系统名称
	 * 
	 * @return 系统名称
	 */
	public static String getOsName() {
		String os = "";
		os = System.getProperty("os.name");
		return os;
	}

	/**
	 * 获取本机MAC地址(WIN7,XP未测试)
	 * 
	 * @return 本机MAC地址
	 * @throws Exception
	 */
	public static String getMACAddress() {
		StringBuffer sb = new StringBuffer();
		try {
			InetAddress ia = InetAddress.getLocalHost();// 获取本地IP对象
			// 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
			byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
			// 下面代码是把mac地址拼装成String
			for (int i = 0; i < mac.length; i++) {
				if (i != 0) {
					sb.append("-");
				}
				// mac[i] & 0xFF 是为了把byte转化为正整数
				String s = Integer.toHexString(mac[i] & 0xFF);
				sb.append(s.length() == 1 ? 0 + s : s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 把字符串所有小写字母改为大写成为正规的mac地址并返回
		return sb.toString().toUpperCase();
	}

	// /**
	// * 获取本机MAC地址(XP)
	// *
	// * @return 本机MAC地址
	// */
	// public static String getMACAddress() {
	// String address = "";
	// String os = getOsName();
	// if (os.startsWith("Windows")) {
	// try {
	// String command = "cmd.exe /c ipconfig /all";
	// Process p = Runtime.getRuntime().exec(command);
	// BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
	// String line;
	// while ((line = br.readLine()) != null) {
	// if (line.indexOf("Physical Address") > 0) {
	// int index = line.indexOf(":");
	// index += 2;
	// address = line.substring(index);
	// break;
	// }
	// }
	// br.close();
	// return address.trim();
	// } catch (IOException e) {
	// }
	// } else if (os.startsWith("Linux")) {
	// String command = "/bin/sh -c ifconfig -a";
	// Process p;
	// try {
	// p = Runtime.getRuntime().exec(command);
	// BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
	// String line;
	// while ((line = br.readLine()) != null) {
	// if (line.indexOf("HWaddr") > 0) {
	// int index = line.indexOf("HWaddr") + "HWaddr".length();
	// address = line.substring(index);
	// break;
	// }
	// }
	// br.close();
	// } catch (IOException e) {
	// }
	// }
	// address = address.trim();
	// return address;
	// }

	/**
	 * 根据IP地址获取对应机器的MAC地址
	 * 
	 * @param ipAddress
	 *            IP地址
	 * @return MAC地址
	 */
	public static String getMACAddress(String ipAddress) {
		String str = "", strMAC = "", macAddress = "";
		try {
			Process pp = Runtime.getRuntime().exec("nbtstat -a " + ipAddress);
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("MAC Address") > 1) {
						strMAC = str.substring(str.indexOf("MAC Address") + 14, str.length());
						break;
					}
				}
			}
		} catch (IOException ex) {
			return "Can't Get MAC Address!";
		}
		// 
		if (strMAC.length() < 17) {
			return "Error!";
		}

		macAddress = strMAC.substring(0, 2) + "-" + strMAC.substring(3, 5) + "-" + strMAC.substring(6, 8) + "-" + strMAC.substring(9, 11) + "-"
				+ strMAC.substring(12, 14) + "-" + strMAC.substring(15, 17);
		// 
		return macAddress;
	}

}
