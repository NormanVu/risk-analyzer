package com.danielpacak.riskanalyzer.frontend.repository.jpa;

import javax.sql.DataSource;

import org.springframework.beans.factory.FactoryBean;

import com.danielpacak.riskanalyzer.datasource.api.DataSourceFactory;

public class DataSourceFactoryBean implements FactoryBean<DataSource> {

	private DataSourceFactory dataSourceFactory;

	public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
		this.dataSourceFactory = dataSourceFactory;
	}

	public Class<DataSource> getObjectType() {
		return DataSource.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public DataSource getObject() throws Exception {
		return dataSourceFactory.getDataSource();
	}

}
