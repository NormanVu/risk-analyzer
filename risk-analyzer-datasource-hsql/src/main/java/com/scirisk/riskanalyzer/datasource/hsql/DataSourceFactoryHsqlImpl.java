package com.scirisk.riskanalyzer.datasource.hsql;

import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCDataSource;

import com.scirisk.riskanalyzer.datasource.DataSourceFactory;

public class DataSourceFactoryHsqlImpl implements DataSourceFactory {

	public DataSource getDataSource() {
		JDBCDataSource dataSource = new JDBCDataSource();
		dataSource.setUser("sa");
		dataSource.setPassword("");
		dataSource.setUrl("jdbc:hsqldb:mem:riskanalyzer");
		return dataSource;
	}

}
