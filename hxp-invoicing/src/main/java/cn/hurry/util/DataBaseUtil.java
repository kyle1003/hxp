package cn.hurry.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库工具,用于备份,还原数据库
 * 
 * @author ZhouHao
 * 
 * 
 */
public class DataBaseUtil {

	private static String cmdExec = "cmd";

	private static String cmdComand = "/c ";

	/**
	 * 清空交流平台数据库表 表名标识
	 */
	public static final int TRUNCATE_TABLE_COMMUNICATION = 1;

	/**
	 * 清空消息提醒数据库表 表名标识
	 */
	public static final int TRUNCATE_TABLE_NOTICE = 2;

	/**
	 * 清空考试系统数据库表 表名标识
	 */
	public static final int TRUNCATE_TABLE_TEST = 3;

	/**
	 * 清空KPI数据库数据库表 表名标识
	 */
	public static final int TRUNCATE_TABLE_KPI = 4;

	/**
	 * 清空交流平台数据库表
	 */
	public static final String[] TRUNCATE_TABLES_COMMUNICATION = { "communication_post", "communication_release", "communication_reply",
			"communication_collect" };

	/**
	 * 清空消息提醒数据库表
	 */
	public static final String[] TRUNCATE_TABLES_NOTICE = { "notice", "staff_notice" };

	/**
	 * 清空考试系统数据库表
	 */
	public static final String[] TRUNCATE_TABLES_TEST = { "test_test_paper", "test_test_paper_question" };

	/**
	 * 清空KPI数据库表
	 */
	public static final String[] TRUNCATE_TABLES_KPI = { "kpi_test_paper", "kpi_target_test_paper", "kpi_staff_test_paper", "kpi_staff_target_test_paper" };

	/**
	 * 数据库备份配置文件名称
	 */
	public static final String BACKUP_PROPERTIES_NAME = "mysql_backup";

	/**
	 *配置文件属性键： 查看历史数据的数据库名称
	 */
	public static final String KEY_BACKUP_DATABASE_NAME = "database_backup_name";

	/**
	 *配置文件属性键： 使用的数据库名称
	 */
	public static final String KEY_DATABASENAME = "databaseName";

	/**
	 *配置文件属性键： 用于保存备份数据库文件的路径
	 */
	public static final String KEY_MYSQL_ADDRESS = "address";

	/**
	 *配置文件属性键： 数据库登录帐号
	 */
	public static final String KEY_MYSQL_USERNAME = "username";

	/**
	 *配置文件属性键： 数据库登录密码
	 */
	public static final String KEY_MYSQL_PASSWORD = "password";

	/**
	 *配置文件属性键： 用于保存备份数据库文件的路径
	 */
	public static final String KEY_MYSQ_FILE_PATH = "sqlFilePath";

	private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseUtil.class);

	/**
	 *MYSQL 安装路径 默认为环境变量%MYSQL_PATH%
	 */
	private static String MYSQL_PATH = "%MYSQL_PATH%";

	static {
		try {
			Properties props = System.getProperties(); // 获得系统属性集
			String osName = props.getProperty("os.name"); // 操作系统名称
			LOGGER.info("操作系统名称:" + osName);
			String osArch = props.getProperty("os.arch"); // 操作系统构架
			LOGGER.info("操作系统构架:" + osArch);
			String osVersion = props.getProperty("os.version"); // 操作系统版本
			LOGGER.info("操作系统版本:" + osVersion);
			// 初始化MYSQL 安装路径
			if (osName.contains("Windows") || osName.contains("windows")) {
				cmdExec = "cmd";
				cmdComand = " /c";
				MYSQL_PATH = check();
			}
			if (osName.contains("Linux") || osName.contains("linux")) {
				cmdExec = "/bin/sh";
				cmdComand = " -c";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			runCommand("ping www.baidu.com");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 遍历注册表，查询MySQL的注册表关联
	 */
	private static String check() throws Exception {
		LOGGER.info("初始化MYSQL 安装路径...");
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		process = runtime.exec("cmd /c reg query HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\");
		BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String string = null;
		while ((string = in.readLine()) != null) {
			// process = runtime.exec("cmd /c reg query " + string + " /v DisplayName");
			// BufferedReader name = new BufferedReader(new InputStreamReader(process.getInputStream()));
			// System.out.println(name.readLine());
			String message = queryValue(string, "DisplayName");
			if (message != null && message.contains("MySQL")) {
				String message2 = queryValue(string, "InstallLocation");
				LOGGER.info("初始化MYSQL 安装路径完成");
				return message2;
			}
		}
		in.close();
		process.destroy();
		return null;
	}

	/**
	 * 查询出需要的MySQL服务的安装路径
	 */
	private static String queryValue(String string, String method) throws IOException {
		String pathString = "";
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		BufferedReader br = null;
		process = runtime.exec(cmdExec + cmdComand + "reg query " + string + " /v " + method);
		br = new BufferedReader(new InputStreamReader(process.getInputStream()));
		br.readLine();
		br.readLine();// 去掉前两行无用信息
		if ((pathString = br.readLine()) != null) {
			pathString = pathString.replaceAll(method + "    REG_SZ    ", ""); // 去掉无用信息
			return pathString;
		}
		return pathString;
	}

	/**
	 * 备份数据库
	 * 
	 * @param sql
	 *            文件名
	 * @param sqlpath
	 *            路径
	 * @throws Exception
	 *             失败则抛出异常
	 */
	public static void backup(String sql, String sqlpath) throws Exception {
		String mysqlpaths = MYSQL_PATH;// MYSQL服务安装路径
		if (mysqlpaths == null) {
			throw new Exception("没有找到mysql数据库安装目录");
		}
		String username = PropertiesUtil.getValueByKey(BACKUP_PROPERTIES_NAME, KEY_MYSQL_USERNAME);
		String password = PropertiesUtil.getValueByKey(BACKUP_PROPERTIES_NAME, KEY_MYSQL_PASSWORD);
		String databaseName = PropertiesUtil.getValueByKey(BACKUP_PROPERTIES_NAME, KEY_DATABASENAME);
		String address = PropertiesUtil.getValueByKey(BACKUP_PROPERTIES_NAME, KEY_MYSQL_ADDRESS);
		LOGGER.info("开始备份数据库:[" + databaseName + "]到[" + sqlpath + "] 文件名为:[" + sql + "]");
		try {
			mysqlpaths = mysqlpaths.trim() + "bin" + "\\";
			File backupath = new File(sqlpath);
			if (!backupath.exists()) {
				LOGGER.info(sqlpath + " 目录不存在,自动创建...");
				backupath.mkdir();
			}
			StringBuffer sb = new StringBuffer();
			sb.append("\"" + mysqlpaths);
			sb.append("mysqldump" + "\" ");
			sb.append("--opt ");
			sb.append("-h ");
			sb.append(address);
			sb.append(" ");
			sb.append("--user=");
			sb.append(username);
			sb.append(" ");
			sb.append("--password=");
			sb.append(password);
			sb.append(" ");
			sb.append("--lock-all-tables=true ");
			sb.append("--result-file=");
			sb.append("\"" + sqlpath + "\\");
			sb.append(sql + "\"");
			sb.append(" ");
			sb.append("--default-character-set=utf8 ");
			sb.append(databaseName);
			int status = runCommand(sb.toString());
			// 判断是否执行成功，如果没有执行成功则抛出异常
			if (status != 0) {
				throw new Exception("执行命令失败!错误码：" + status);
			}
		} catch (Exception e1) {
			LOGGER.error(e1.getMessage());
			throw new Exception("备份失败,原因:" + e1.getMessage());
		}
	}

	/**
	 * 恢复数据库
	 * 
	 * @param filepath
	 *            SQL文件完整路径
	 * @param databaseName
	 *            数据库名
	 * @throws Exception
	 *             恢复失败抛出异常
	 */
	public synchronized static void load(String filepath, String databaseName) throws Exception {
		LOGGER.info("开始恢复数据库:[" + databaseName + "]" + "数据库文件:[" + filepath + "]");
		String user = PropertiesUtil.getValueByKey(BACKUP_PROPERTIES_NAME, KEY_MYSQL_USERNAME);
		String pass = PropertiesUtil.getValueByKey(BACKUP_PROPERTIES_NAME, KEY_MYSQL_PASSWORD);
		if (MYSQL_PATH == null) {
			throw new Exception("没有找到mysql数据库安装目录");
		}
		String mysqlpaths = MYSQL_PATH.trim() + "bin" + "\\";// MYSQL bin目录
		// 创建新数据库
		String stmt1 = "\"" + mysqlpaths + "mysqladmin\" -u " + user + " -p" + pass + " create " + databaseName; // -p后面加的是你的密码
		// 恢复数据
		String stmt2 = "mysql -u " + user + " -p" + pass + " " + databaseName + " < \"" + filepath + "\"";
		try {
			LOGGER.info("创建数据库:[" + databaseName + " ]");
			// 创建数据库,数据库已存在则命令执行失败(不影响下面命令的执行)
			int createStatus = runCommand(stmt1);
			LOGGER.info("创建数据库执行完成,状态:" + createStatus);
			// 执行命令
			LOGGER.info("恢复数据库:[" + databaseName + " ]");
			int loadStatus = runCommand(new String[] { cmdExec.trim(), cmdComand.trim(), stmt2 }, null, new File(mysqlpaths));
			// 判断恢复数据是否执行成功,未执行成功抛出异常
			if (loadStatus != 0) {
				throw new Exception("执行命令时发生错误!错误码:" + loadStatus);
			}
			LOGGER.info("恢复数据库:[" + databaseName + "]" + "成功");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new Exception("恢复失败,原因:" + e.getMessage());
		}
	}

	/**
	 * 在指定环境和工作目录的独立进程中执行指定的命令和变量。
	 * 
	 * @param cmdarray
	 * @param envp
	 * @param dir
	 *            工作目录
	 * @return
	 */
	public synchronized static int runCommand(String[] cmdarray, String[] envp, File dir) {
		try {
			LOGGER.info("开始执行命令....");
			LOGGER.info("命令----" + cmdarray[2]);
			final Process process = Runtime.getRuntime().exec(cmdarray, envp, dir);
			return getProcessInfo(process);
		} catch (Exception e) {
			e.printStackTrace();
			return -1000;
		}
	}

	/**
	 * 在单独的进程中执行指定的字符串命令。
	 * 
	 * @param command
	 * @return
	 */
	public synchronized static int runCommand(String command) {
		try {
			LOGGER.info("开始执行命令....");
			LOGGER.info("命令:" + command);
			final Process process = Runtime.getRuntime().exec(new String(command.getBytes(), "gbk"));
			return getProcessInfo(process);
		} catch (Exception e) {
			e.printStackTrace();
			return -1000;
		}
	}
	

	/**
	 * 命令进程执行信息
	 * 
	 * @param process
	 * @return 执行结果0为成功
	 */
	public synchronized static int getProcessInfo(final Process process) {
		try {
			BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream(), "gbk"));
			String line = null;
			Runnable rr = new Runnable() {
				public void run() {
					try {
						BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
						String line2 = null;
						while ((line2 = error.readLine()) != null) {
							LOGGER.error("错误信息\t" + line2);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			(new Thread(rr)).start();
			while ((line = output.readLine()) != null) {
				LOGGER.info("执行信息\t" + line);
			}
			int exitValue = process.exitValue();
			// int exitValue = process.waitFor();
			LOGGER.info("执行命令完成 状态码：" + exitValue);
			return exitValue;
		} catch (Exception e) {
			e.printStackTrace();
			return -1000;
		}
	}
}
