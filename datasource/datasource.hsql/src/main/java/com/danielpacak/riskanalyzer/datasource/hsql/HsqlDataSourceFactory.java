package com.danielpacak.riskanalyzer.datasource.hsql;

import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCDataSource;

import com.danielpacak.riskanalyzer.datasource.api.DataSourceFactory;

public class HsqlDataSourceFactory implements DataSourceFactory {

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
		JDBCDataSource dataSource = new JDBCDataSource();
		dataSource.setUser(user);
		dataSource.setPassword(password);
		dataSource.setUrl(url);
		return dataSource;
	}

}
