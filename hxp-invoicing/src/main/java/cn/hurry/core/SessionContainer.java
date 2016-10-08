package cn.hurry.core;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import cn.hurry.po.user.User;

/**
 * 会话容器
 * 
 * @author zh.sqy@qq.com
 * 
 */
public class SessionContainer {

	private static SessionContainer instance;

	private HashMap<String, HttpSession> sessionMap;

	private SessionContainer() {
		sessionMap = new HashMap<String, HttpSession>();
	}

	/**
	 * 获取会话容器
	 * 
	 * @return 会话容器
	 */
	public static SessionContainer getInstance() {
		if (instance == null) {
			instance = new SessionContainer();
		}
		return instance;
	}

	/**
	 * 保存会话
	 * 
	 * @param session
	 *            会话
	 */
	public synchronized void addSession(HttpSession session) {
		if (session != null) {
			sessionMap.put(session.getId(), session);
		}
	}

	/**
	 * 删除会话
	 * 
	 * @param session
	 *            会话
	 */
	public synchronized void delSession(HttpSession session) {
		if (session != null) {
			sessionMap.remove(session.getId());
		}
	}

	/**
	 * 根据会话编号获取会话
	 * 
	 * @param sessionId
	 *            会话编号
	 * @return 会话
	 */
	public synchronized HttpSession getSession(String sessionId) {
		if (sessionId == null) {
			return null;
		}
		return sessionMap.get(sessionId);
	}

	/**
	 * 获取会话数量
	 * 
	 * @return 会话数量
	 */
	public synchronized int getSessionAmount() {
		return sessionMap.size();
	}

	/**
	 * 根据用户编号获取会话
	 * 
	 * @param userId
	 *            用户编号
	 * @return 会话
	 */
	public synchronized HttpSession getSessionByUser(int userId) {
		ArrayList<HttpSession> sessionList = new ArrayList<HttpSession>(sessionMap.values());
		HttpSession userSession = null;
		for (HttpSession session : sessionList) {
			User user = (User) session.getAttribute("user");
			if (user != null && user.getId() == userId) {
				userSession = session;
				break;
			}
		}
		return userSession;
	}

	/**
	 * 移除所有会话
	 */
	public synchronized void removeAllSession() {
		ArrayList<HttpSession> sessionList = new ArrayList<HttpSession>(sessionMap.values());
		for (HttpSession session : sessionList) {
			HttpSession containerSession = getSession(session.getId());
			containerSession.invalidate();
			delSession(session);
		}
	}
}
