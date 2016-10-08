package cn.hurry.util;

import java.util.HashMap;

/**
 * 基础字符串常量类
 * 
 * @author zh.sqy@qq.com
 * 
 */
public class BaseString {
	// 文件格式
	public static final HashMap<String, String> extMap = new HashMap<String, String>();
	// 加载文件格式
	static {
		extMap.put("image", "gif,jpg,jpeg,png,bmp,GIF,JPG,JPEG,PNG,BMP");
		extMap.put("flash", "swf,flv,SWF,FLV");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,SWF,FLV,MP3,WAV,WMA,WMV,MID,AVI,MPG,ASF,RM,RMVB");
		extMap.put("file", "doc,docx,wps,et,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,DOC,DOCX,WPS,ET,XLS,XLSX,PPT,HTM,HTML,TXT,ZIP,RAR,GZ,BZ2");
		extMap.put("illegalfile", "exe,cmd,bat,js,vbs,msi,scr,EXE,CMD,BAT,JS,VBS,MSI,SCR");
		extMap.put("sqlFile", "sql,SQL");
		extMap.put("text", "txt,TXT");
		extMap.put("word", "doc,docx,wps,DOC,DOCX,WPS");
		extMap.put("excel", "et,xls,xlsx,ET,XLS,XLSX");
		extMap.put("ppt", "ppt,PPT");
		extMap.put("rar", "zip,rar,gz,bz2,ZIP,RAR,GZ,BZ2");
	}

	/**
	 * 上传路径：图片
	 */
	public static final String UPLOAD_PATH_IMG = "upload/otherImg";

	/**
	 * 上传路径：文件
	 */
	public static final String UPLOAD_PATH_FILE = "upload/file";

	/**
	 * 上传路径：员工照片
	 */
	public static final String UPLOAD_PATH_STAFFPHOTO = "upload/staffPhotoImg";;

	/**
	 * 上传路径：SQL文件
	 */
	public static final String UPLOAD_PATH_SQL = "sqlFile/upload";

	/**
	 * 返回信息键
	 */
	public static final String KEY_MESSAGE = "controllerMessage";

	/**
	 * 返回错误键
	 */
	public static final String KEY_ERROR = "controllerError";

	/**
	 * 返回异常键
	 */
	public static final String KEY_EXCEPTION = "controllerException";

	/**
	 * 提示：帐号未绑定员工
	 */
	public static final String USER_NOT_BINDING_STAFF = "您的帐号未绑定员工!";

	/**
	 * 提示：服务器异常
	 */
	public static final String INFO_EXCEPTION = "服务器异常！请联系管理员！";

	/**
	 * 提示：页面参数错误（例如浏览器修改地址）
	 */
	public static final String INFO_PARAM_ERROR = "参数错误！不存在的页面！";

	/**
	 * 提示：查询参数错误
	 */
	public static final String INFO_SEARCH_PARAM_ERROR = "查询参数错误！请重新输入！";

	/**
	 * 提示：权限不足
	 */
	public static final String INFO_NO_OPERATE = "权限不足！请登录后正常访问后台！";

	/**
	 * 提示：提交成功
	 */
	public static final String INFO_ADD_SUCCESS = "提交成功！";

	/**
	 * 提示：修改成功
	 */
	public static final String INFO_UPDATE_SUCCESS = "修改成功！";

	/**
	 * 提示：删除成功
	 */
	public static final String INFO_DEL_SUCCESS = "删除成功！";

	/**
	 * 提示：批量导入后最名判断
	 */
	public static final String FILE_SUFFIX = "xls|et";

	/**
	 * 默认员工图片
	 */
	public static final String DEFAULT_STAFF_PHOTO_IMG = "upload/staffPhotoImg/defaultPhotoImg.jpg";

	/**
	 *发布试卷自动生成的系统消息 <br>
	 * 
	 */
	public static final String NOTICE_CREATE_TEST_PAPER_TITLE = "您有一张试卷需要完成!";

	/**
	 *发布文章被回复后自动生成的系统消息 <br>
	 * 
	 */
	public static final String COMMUNICATION_REPLY_AUTO_NOTICE_TITLE = "您的文章(${title})有了新的评论!";

	/**
	 *发布试卷自动生成的系统消息 <br>
	 * 将${staff}替换为实际员工 <br>
	 * 将${time}替换为预计完成时间<br>
	 * 将${title}替换为标题 <br>
	 * 将${nowTime}替换为当前时间<br>
	 */
	public static final String NOTICE_CREATE_TEST_PAPER_CONTENT = "<p style=\"text-align:center;\">系统提醒</p><p>"
			+ "<span style=\"color:#333333;\">${staff}</span>您好:</p><p>&nbsp; &nbsp;" + " 您有一个新的试卷(<span style=\"color:#337FE5;\">${title}</span>)"
			+ "需要在(<span style=\"color:#E53333;\">${time}</span>)之前完成,请到<span style=\"color:#E53333;\">"
			+ "个人资料</span>-<span style=\"color:#E53333;\">我的考试</span>中查看并及时完成!" + "</p><p><br /></p><p style=\"text-align:right;\">${nowTime}</p>";

	/**
	 * 有人回复文章自动生成的系统消息 <br>
	 * 将${staff}替换为实际员工 <br>
	 * 将${title}替换为文章标题 <br>
	 * 将${nowTime}替换为当前时间<br>
	 */
	public static final String COMMUNICATION_REPLY_AUTO_NOTICE_CONTENT = "<p style=\"text-align:center;\">系统提醒</p><p>"
			+ "<span style=\"color:#333333;\">${staff}</span>您好:</p><p>&nbsp; &nbsp;" + " 您发布的文章(<span style=\"color:#337FE5;\">${title}</span>)"
			+ "有了新回复,请到<span style=\"color:#E53333;\">" + "资讯中心</span>-<span style=\"color:#E53333;\">交流平台</span>中查看!"
			+ "</p><p><br /></p><p style=\"text-align:right;\">${nowTime}</p>";

	/**
	 * 发布文章自动生成的系统消息 <br>
	 * 将${title}替换为文章标题 <br>
	 * 将${nowTime}替换为当前时间<br>
	 */
	public static final String COMMUNICATION_RELEASE_AUTO_NOTICE_CONTENT = "<p style=\"text-align:center;\">系统提醒</p><p>"
		+ "<span style=\"color:#333333;\"></span>您好:</p><p>&nbsp; &nbsp;" + " 你有一分新的文章(<span style=\"color:#337FE5;\">${title}</span>)"
		+ "需要查看,请到<span style=\"color:#E53333;\">" + "资讯中心</span>-<span style=\"color:#E53333;\">交流平台</span>中查看!"
		+ "</p><p><br /></p><p style=\"text-align:right;\">${nowTime}</p>";

	/**
	 * 人事提醒
	 */
	public static final String NOTICE_YOU_HAVA_A_PERSONNEL = "您有一条人事提醒!";

	/**
	 * 请销假后自动生成的提醒内容<br>
	 * 将${staff}替换为请假员工 <br>
	 * 将${leaveTime}替换为请假时间 <br>
	 * 将${shouldComeBackTime}替换为应销假时间 <br>
	 * 将${telNumber}替换联系电话 <br>
	 * 将${nowTime}替换为当前时间<br>
	 */
	public static final String NOTICE_STAFF_LEAVE_ALERT_CONTENT = "<p style=\"text-align:center;\">系统提醒</p><p>"
			+ "<span style=\"color:#333333;\">管理员</span>您好:</p><p>&nbsp; &nbsp;" + " 员工(<span style=\"color:#337FE5;\">${staff}</span>)"
			+ "在(<span style=\"color:#E53333;\">${leaveTime}</span>)时请假离开,应于<span style=\"color:#E53333;\">"
			+ "${shouldComeBackTime}</span>前销假,请及时通知!他的联系电话为:${telNumber}" + "</p><p><br /></p><p style=\"text-align:right;\">${nowTime}</p>";

	/**
	 * 合同将到期自动提醒内容
	 * 
	 */
	public static final String NOTICE_STAFF_PACT_ALERT_CONTENT = "<p style=\"text-align:center;\">系统提醒</p><p>"
			+ "<span style=\"color:#333333;\">管理员</span>您好:</p><p>&nbsp; &nbsp;" + " 以下员工:(<span style=\"color:#337FE5;\">${staff}</span>)"
			+ "的合同即将到期,请及时查看处理;" + "</p><p><br /></p><p style=\"text-align:right;\">${nowTime}</p>";
	
	public static final String NOTICE_STAFF_RETIRE_ALERT_CONTENT = "<p style=\"text-align:center;\">系统提醒</p><p>"
		+ "<span style=\"color:#333333;\">管理员</span>您好:</p><p>&nbsp; &nbsp;" + " 以下员工:(<span style=\"color:#337FE5;\">${staff}</span>)"
		+ "退休年龄马上就要到了,请及时查看处理;" + "</p><p><br /></p><p style=\"text-align:right;\">${nowTime}</p>";

}
