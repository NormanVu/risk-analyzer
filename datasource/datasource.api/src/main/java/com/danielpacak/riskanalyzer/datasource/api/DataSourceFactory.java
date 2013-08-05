package com.danielpacak.riskanalyzer.datasource.api;

import javax.sql.DataSource;

/**
 * A factory for JDBC connection factories.
 */
public interface DataSourceFactory {

	DataSource getDataSource();

}
