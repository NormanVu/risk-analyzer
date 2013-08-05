package com.danielpacak.riskanalyzer.datasource.postgresql.heroku;

import java.net.URI;

import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;

import com.danielpacak.riskanalyzer.datasource.api.DataSourceFactory;

/**
 * Heroku automatically provisions a small database when you create a Java
 * application and sets the <code>DATABASE_URL</code> environment variable to a
 * URL of the format:
 * 
 * <pre>
 * postgres://user:password@hostname:port/dbname
 * </pre>
 */
public class PostgresqlHerokuDataSourceFactory implements DataSourceFactory {

	private URI databaseUri;

	public PostgresqlHerokuDataSourceFactory(URI databaseUri) {
		this.databaseUri = databaseUri;
	}

	public DataSource getDataSource() {
		PGSimpleDataSource dataSource = new PGSimpleDataSource();
		dataSource.setUser(databaseUri.getUserInfo().split(":")[0]);
		dataSource.setPassword(databaseUri.getUserInfo().split(":")[1]);
		dataSource.setServerName(databaseUri.getHost());
		dataSource.setPortNumber(databaseUri.getPort());
		dataSource.setDatabaseName(databaseUri.getPath().substring(1));

		return dataSource;
	}

}
