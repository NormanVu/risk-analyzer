package com.scirisk.riskanalyzer.datasource.mysql;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.scirisk.riskanalyzer.datasource.DataSourceFactory;

public class DataSourceFactoryMySqlImpl implements DataSourceFactory {

	private String user;
	private String password;
	private String url;

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public DataSource getDataSource() {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser(user);
		dataSource.setPassword(password);
		dataSource.setUrl(url);
		return dataSource;
	}

}
