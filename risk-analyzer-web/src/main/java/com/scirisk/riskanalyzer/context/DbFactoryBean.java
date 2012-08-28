package com.scirisk.riskanalyzer.context;

import org.springframework.beans.factory.FactoryBean;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class DbFactoryBean implements FactoryBean<DB> {

	private Mongo mongo;
	private String databaseName;

	public void setMongo(Mongo mongo) {
		this.mongo = mongo;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public DB getObject() throws Exception {
		return mongo.getDB(databaseName);
	}

	public Class<DB> getObjectType() {
		return DB.class;
	}

	public boolean isSingleton() {
		return true;
	}

}
