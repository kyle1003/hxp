package cn.hurry.manage;

import java.util.HashMap;

import cn.hurry.po.authorize.Authorize;
import cn.hurry.service.authorize.AuthorizeService;

/**
 * 授权管理类
 * 
 * @author zh.sqy@qq.com
 * 
 */

public class AuthorizeManager {

	private static HashMap<String, String> AUTHORIZE_MAP = new HashMap<String, String>();

	private static final String AUTHORIZE_CODE = "code";
	static {
		init();
	}

	/**
	 * 初始化设置
	 */
	public static void init() {
		synchronized (AUTHORIZE_MAP) {
			try {
				Authorize authorize = new AuthorizeService().getAuthorize();
				if (authorize == null) {
					authorize = new Authorize();
					authorize.setCode("");
				}
				AUTHORIZE_MAP.put(AUTHORIZE_CODE, authorize.getCode());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 添加授权码
	 * 
	 * @param authorizeCode
	 *            授权码
	 */
	public static void addAuthorizeCode(String authorizeCode) {
		synchronized (AUTHORIZE_MAP) {
			try {
				AuthorizeService authorizeService = new AuthorizeService();

				Authorize authorize = new Authorize();
				authorize.setCode(authorizeCode);

				Authorize dbAuthorize = authorizeService.getAuthorize();
				if (dbAuthorize == null) {
					authorizeService.addAuthorize(authorize);
				} else {
					authorizeService.updateAuthorize(authorize);
				}

				AUTHORIZE_MAP.put(AUTHORIZE_CODE, authorizeCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取授权码
	 * 
	 * @return 授权码
	 */
	public static String getAuthorizeCode() {
		synchronized (AUTHORIZE_MAP) {
			String authorizeCode = AUTHORIZE_MAP.get(AUTHORIZE_CODE);
			return authorizeCode == null ? "" : authorizeCode;
		}
	}

}
