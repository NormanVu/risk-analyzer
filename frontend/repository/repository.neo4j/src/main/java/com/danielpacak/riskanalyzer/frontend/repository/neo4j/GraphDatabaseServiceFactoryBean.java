package com.danielpacak.riskanalyzer.frontend.repository.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.beans.factory.FactoryBean;

public class GraphDatabaseServiceFactoryBean implements FactoryBean<GraphDatabaseService> {

	private String path;

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public GraphDatabaseService getObject() throws Exception {
		return new GraphDatabaseFactory().newEmbeddedDatabase(path);
	}

	@Override
	public Class<GraphDatabaseFactory> getObjectType() {
		return GraphDatabaseFactory.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
