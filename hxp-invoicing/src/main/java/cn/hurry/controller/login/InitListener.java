package cn.hurry.controller.login;



import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



public class InitListener implements ServletContextListener {

	/**
	 * 服务器关闭时执行
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		//TimerUtils.stopTimer();
	}

	/**
	 * 服务器开启时执行
	 */
	public void contextInitialized(ServletContextEvent sce) {
		// 系统的初始化工作
	}

}
