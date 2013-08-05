package com.danielpacak.riskanalyzer.datasource.postgresql.heroku;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.danielpacak.riskanalyzer.datasource.api.DataSourceFactory;
import com.danielpacak.riskanalyzer.datasource.postgresql.heroku.PostgresqlHerokuDataSourceFactory;

/**
 * Tests for {@link PostgresqlHerokuDataSourceFactory}.
 */
public class PostgresqlHerokuDataSourceFactoryTest {

	@Test
	public void testGetDataSource() throws Exception {
		URI databaseUri = new URI("postgres://user:password@hostname:9090/dbname");
		DataSourceFactory factory = new PostgresqlHerokuDataSourceFactory(databaseUri);
		PGSimpleDataSource dataSource = (PGSimpleDataSource) factory.getDataSource();
		assertEquals("user", dataSource.getUser());
		assertEquals("password", dataSource.getPassword());
		assertEquals("hostname", dataSource.getServerName());
		assertEquals(9090, dataSource.getPortNumber());
		assertEquals("dbname", dataSource.getDatabaseName());
	}

	@Test
	public void testGetDataSourceThroughApplicationContext() throws Exception {
		System.setProperty("DATABASE_URL", "postgres://user:password@hostname:9090/dbname");
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"META-INF/spring/com.danielpacak.riskanalyzer.datasource.postgresql.heroku.xml");
		DataSourceFactory factory = ctx.getBean(DataSourceFactory.class);
		PGSimpleDataSource dataSource = (PGSimpleDataSource) factory.getDataSource();
		assertEquals("user", dataSource.getUser());
		assertEquals("password", dataSource.getPassword());
		assertEquals("hostname", dataSource.getServerName());
		assertEquals(9090, dataSource.getPortNumber());
		assertEquals("dbname", dataSource.getDatabaseName());
	}

}
