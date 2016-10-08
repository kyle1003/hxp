package cn.hurry.data;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MyBatis的SqlSession工厂类
 * 
 * @author zh.sqy@qq.com
 * 
 * @version $Revision: 208 $ <br>
 *          $LastChangedBy: zh.sqy@qq.com $ <br>
 *          $LastChangedDate: 2012-03-12 16:35:19 +0800 (Mon, 12 Mar 2012) $
 */
public class SessionFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(SessionFactory.class);

	private static SqlSessionFactory sessionFactory;


	static {
		LOGGER.info("initializing...");
		String resource = "cn/hurry/data/SqlMapConfig.xml";
		try {
			Reader reader = Resources.getResourceAsReader(resource);
			sessionFactory = new SqlSessionFactoryBuilder().build(reader);
			LOGGER.info("initialized!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static SqlSession openSession() {
		return sessionFactory.openSession();
	}

}