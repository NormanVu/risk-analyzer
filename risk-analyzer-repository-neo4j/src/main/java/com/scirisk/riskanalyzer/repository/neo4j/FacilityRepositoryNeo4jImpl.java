package com.scirisk.riskanalyzer.repository.neo4j;

import java.util.List;

import org.neo4j.graphdb.GraphDatabaseService;

import com.scirisk.riskanalyzer.domain.Facility;
import com.scirisk.riskanalyzer.repository.FacilityRepository;

public class FacilityRepositoryNeo4jImpl implements FacilityRepository {

	GraphDatabaseService databaseService;

	public FacilityRepositoryNeo4jImpl(GraphDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@Override
	public Facility save(Facility node) {
		throw new IllegalStateException("I'm not implemented yet");
	}

	@Override
	public void delete(String nodeId) {
		throw new IllegalStateException("I'm not implemented yet");
	}

	@Override
	public Facility findOne(String nodeId) {
		throw new IllegalStateException("I'm not implemented yet");
	}

	@Override
	public List<Facility> findAll() {
		throw new IllegalStateException("I'm not implemented yet");
	}

}
