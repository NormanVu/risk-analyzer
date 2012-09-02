package com.scirisk.riskanalyzer.datasource.mysql;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.scirisk.riskanalyzer.datasource.DataSourceFactory;

public class DataSourceFactoryMySqlImpl implements DataSourceFactory {
	
	public DataSource getDataSource() {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("risk-analyzer");
		dataSource.setPassword("secret");
		dataSource.setUrl("jdbc:mysql://localhost/risk-analyzer");
		return dataSource;
	}

}
