package cn.hurry.util;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * WEB CHINESE 短信发送接口
 * 
 * @author ZhouHao
 * 
 */
public class SendMsg_webchinese {
	/**
	 * 没有此用户
	 */
	public final static int NOT_FOUND_UID = -1;
	/**
	 * 密匙错误
	 */
	public final static int KEY_ERROR = -2;
	/**
	 * 短信剩余条数不足
	 */
	public final static int MESSAGE_NUMBER_NOT_ENOUGN = -3;
	/**
	 * 该用户被禁用
	 */
	public final static int UID_FORBIFFRN = -11;
	/**
	 * 短信内容出现非法字符
	 */
	public final static int MESSAGE_HAS_ILLEGALITY_CODE = -14;
	/**
	 * 手机号码为空
	 */
	public final static int PHONE_NUMBER_ISNULL = -41;
	/**
	 * 短信内容为空
	 */
	public final static int MESSAGE_ISNULL = -42;

	/**
	 * 获取短信剩余条数
	 * 
	 * @param uid
	 *            服务提供商注册场合
	 * @param key
	 *            短信密匙
	 * @return 成功返回短信剩余条数大于等于0 失败返回短信剩余条数小于0
	 * @throws Exception
	 */
	public static int getMsgNum(String uid, String key) throws Exception {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://sms.webchinese.cn/web_api/SMS/");
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");// 在头文件中设置转码
		NameValuePair[] data = { new NameValuePair("Uid", uid), // 注册的用户名
				new NameValuePair("Action", "SMS_Num"), new NameValuePair("Key", key) };
		post.setRequestBody(data);
		client.executeMethod(post);
		String result = new String(post.getResponseBodyAsString().getBytes("utf8"));
		post.releaseConnection();
		return Integer.parseInt(result);
	}

	public static String getMsg(String url) throws Exception {
		HttpClient client = new HttpClient();
		// PostMethod post = new PostMethod(url);
		GetMethod get = new GetMethod(url);
		//get.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");// 在头文件中设置转码
		client.executeMethod(get);
		String result = new String(get.getResponseBodyAsString());
		get.releaseConnection();
		return result;
	}

	/**
	 * 发送短信
	 * 
	 * @param uid
	 *            服务提供商注册帐号
	 * @param key
	 *            短信密匙
	 * @param message
	 *            短信内容
	 * @param phoneNum
	 *            发送至
	 * @return 成功则返回短信发送条数 失败返回小于0的数字
	 * @throws Exception
	 */
	public static int sendMsg(String uid, String key, String phoneNum, String message) throws Exception {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://utf8.sms.webchinese.cn/");
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");// 在头文件中设置转码
		NameValuePair[] data = { 
				new NameValuePair("Uid", uid), // 注册的用户名
				new NameValuePair("Key", key), //密匙
				new NameValuePair("smsMob", phoneNum), // 手机号码
				new NameValuePair("smsText", message) //短信内容
			};
		post.setRequestBody(data);
		client.executeMethod(post);
		String result = new String(post.getResponseBodyAsString().getBytes("utf8"));
		post.releaseConnection();
		return Integer.parseInt(result);
	}

}