package com.scirisk.riskanalyzer.repository.neo4j;

import java.util.List;

import com.scirisk.riskanalyzer.domain.Facility;
import com.scirisk.riskanalyzer.repository.FacilityRepository;

public class FacilityRepositoryNeo4jImpl implements FacilityRepository {

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
