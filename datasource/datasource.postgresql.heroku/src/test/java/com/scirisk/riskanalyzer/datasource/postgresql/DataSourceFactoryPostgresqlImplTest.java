package com.scirisk.riskanalyzer.datasource.postgresql;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.scirisk.riskanalyzer.datasource.DataSourceFactory;

public class DataSourceFactoryPostgresqlImplTest {

	@Test
	public void testGetDataSource() throws Exception {
		URI databaseUri = new URI(
				"postgres://user:password@hostname:9090/dbname");
		DataSourceFactory factory = new DataSourceFactoryPostgresqlImpl(
				databaseUri);
		PGSimpleDataSource dataSource = (PGSimpleDataSource) factory
				.getDataSource();
		assertEquals("user", dataSource.getUser());
		assertEquals("password", dataSource.getPassword());
		assertEquals("hostname", dataSource.getServerName());
		assertEquals(9090, dataSource.getPortNumber());
		assertEquals("dbname", dataSource.getDatabaseName());
	}

	@Test
	public void testGetDataSourceThroughApplicationContext() throws Exception {
		System.setProperty("DATABASE_URL",
				"postgres://user:password@hostname:9090/dbname");
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"META-INF/spring/datasource.xml");
		DataSourceFactory factory = ctx.getBean(DataSourceFactory.class);
		PGSimpleDataSource dataSource = (PGSimpleDataSource) factory
				.getDataSource();
		assertEquals("user", dataSource.getUser());
		assertEquals("password", dataSource.getPassword());
		assertEquals("hostname", dataSource.getServerName());
		assertEquals(9090, dataSource.getPortNumber());
		assertEquals("dbname", dataSource.getDatabaseName());
	}

}
