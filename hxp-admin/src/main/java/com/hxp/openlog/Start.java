package com.hxp.openlog;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * Created by Administrator on 2016/5/31.
 */
public class Start implements BeanFactoryPostProcessor {
    Logger logger = LogManager.getLogger(Start.class.getName());
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        logger.info(" 胡小朋的项目初始化..");
        logger.debug("                                             ");
        logger.debug("                   _ooOoo_                          ");
        logger.debug("                  o8888888o                         ");
        logger.debug("                  88\" . \"88                       ");
        logger.debug("                  (| -_- |)                         ");
        logger.debug("                  O\\  =  /O                        ");
        logger.debug("               ____/`---'\\____                     ");
        logger.debug("             .'  \\\\|     |//  `.                  ");
        logger.debug("            /  \\\\|||  :  |||//  \\                ");
        logger.debug("           /  _||||| -:- |||||-  \\                 ");
        logger.debug("           |   | \\\\\\  -  /// |   |               ");
        logger.debug("           | \\_|  ''\\---/''  |   |                ");
        logger.debug("           \\  .-\\__  `-`  ___/-. /                ");
        logger.debug("         ___`. .'  /--.--\\  `. . __                ");
        logger.debug("      .\"\" '<  `.___\\_<|>_/___.'  >'\"\".         ");
        logger.debug("     | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |          ");
        logger.debug("     \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /        ");
        logger.debug("======`-.____`-.___\\_____/___.-`____.-'======      ");
        logger.debug("                   `=---='                          ");
        logger.debug("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^       ");
        logger.debug("佛主驾到    开怀大笑    服务正常    喜气洋洋            ");
    }
}
