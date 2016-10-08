package cn.hurry.data;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

/**
 * Mybatis DBCP 数据源
 */
public final class DbcpDataSourceFactory extends UnpooledDataSourceFactory {
	public DbcpDataSourceFactory() {
		this.dataSource = new BasicDataSource();
	}
}