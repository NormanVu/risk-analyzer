package com.scirisk.riskanalyzer.datasource;

import javax.sql.DataSource;

/**
 * A factory for JDBC connection factories.
 *
 */
public interface DataSourceFactory {

	DataSource getDataSource();

}
