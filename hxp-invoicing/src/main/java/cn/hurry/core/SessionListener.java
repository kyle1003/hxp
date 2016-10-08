package cn.hurry.core;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 会话监听器
 * 
 * @author zh.sqy@qq.com
 *
 */
public class SessionListener implements HttpSessionListener {

	private SessionContainer sessionContainer = SessionContainer.getInstance();

	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		sessionContainer.addSession(httpSessionEvent.getSession());
	}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		HttpSession session = httpSessionEvent.getSession();
		sessionContainer.delSession(session);
	}

}
